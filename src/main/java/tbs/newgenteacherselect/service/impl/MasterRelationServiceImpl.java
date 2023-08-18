package tbs.newgenteacherselect.service.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import tbs.framework.async.annotations.LockIt;
import tbs.framework.error.NetError;
import tbs.framework.redis.IRedisService;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.MasterRelationDao;
import tbs.newgenteacherselect.dao.StudentDao;
import tbs.newgenteacherselect.dao.StudentLevelDao;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.MasterRelation;
import tbs.pojo.StudentLevel;
import tbs.pojo.dto.MasterRelationVO;
import tbs.pojo.dto.StudentUserDetail;
import tbs.pojo.dto.TeacherDetail;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MasterRelationServiceImpl implements MasterRelationService {


    @Resource
    IRedisService redisService;


    @Resource
    StudentDao studentDao;

    @Resource
    StudentLevelDao studentLevelDao;

    @Resource
    TeacherService teacherService;
    @Resource
    MasterRelationDao masterRelationDao;

    private static class RelationKey {
        @Override
        public String toString() {
            return "RelationKey{" +
                    "master='" + master + '\'' +
                    ", configItem=" + configItem +
                    '}';
        }

        private String master;
        private Integer configItem;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RelationKey)) return false;

            RelationKey key = (RelationKey) o;

            if (master != null ? !master.equals(key.master) : key.master != null) return false;
            if (configItem != null ? !configItem.equals(key.configItem) : key.configItem != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = master != null ? master.hashCode() : 0;
            result = 31 * result + (configItem != null ? configItem.hashCode() : 0);
            return result;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public Integer getConfigItem() {
            return configItem;
        }

        public void setConfigItem(Integer configItem) {
            this.configItem = configItem;
        }

        public RelationKey(String master, Integer configItem) {
            this.master = master;
            this.configItem = configItem;
        }
    }

    private static final String RELATIONMAP = "Relation_MAP";

    @Override
//    @Scheduled(fixedRate = 60, timeUnit = TimeUnit.SECONDS)
//    @Async
    public void calLeftForMaster() {
        log.debug("定时任务-导师名额详情列表");
        Map<String, List<MasterRelationVO>> hash = new HashMap();
        for (Integer id : masterRelationDao.allTemplateItemIds()) {
            List<MasterRelationVO> list = masterRelationDao.listMasterByHasStudent(id);

            for (MasterRelationVO vo : list) {
                String key = new RelationKey(vo.getMaster(), id).toString();
                if (!hash.containsKey(key)) {
                    List<MasterRelationVO> ls = new LinkedList<>();
                    hash.put(key.toString(), ls);
                }
                hash.get(key).add(vo);
            }
        }
        redisService.set(RELATIONMAP, hash);
    }

    @Override
    public List<MasterRelationVO> listStatus(int config, String master) {
        Map mp = redisService.get(RELATIONMAP, Map.class);
        RelationKey key = new RelationKey(master, config);
        JSONArray object = (JSONArray) mp.get(key.toString());
        List list = object.stream().collect(Collectors.toList());
        List<MasterRelationVO> relationVOS = new LinkedList<>();
        for (Object o : list) {
            JSONObject ob = (JSONObject) o;
            relationVOS.add(JSON.toJavaObject(ob, MasterRelationVO.class));
        }
        return relationVOS;
    }

    @Override
    public TeacherDetail getMasterByStudent(String student) {
        MasterRelation relation = masterRelationDao.findByStudent(student);
        if (relation == null)
            return null;
        return teacherService.findTeacher(relation.getMasterPhone());
    }

    @Override
    @LockIt("SELECT_MASTER")
    public Integer selectMaster(String student, String master) throws NetError {
        StudentLevel studentmodel = studentLevelDao.selectById(student);
        if (studentmodel == null)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND, "该学生 " + student + " 未分级");
        try {
            int result = masterRelationDao.selectMaster(student, master, studentmodel.getLevelId());
            if (result < 1)
                throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND, String.format("针对学生%s级别的，导师%s名额没有了", student, master));
        } catch (DuplicateKeyException duplicateKeyException) {
            throw NetErrorEnum.makeError(NetErrorEnum.HAS_MORE_NODE, "该生已有导师");
        }
        return 1;
    }

    @Override
    @LockIt("SELECT_MASTER")
    public Integer returnbackMaster(String student, String master) {
        masterRelationDao.unselectMaster(student, master);
        return 1;
    }

    @Override
    public List<StudentUserDetail> listStudentByMasterAndConfig(String master, int config) {
        List<MasterRelation> relations = masterRelationDao.findByMasterAndConfig(master, config);
        List<StudentUserDetail> list = new LinkedList<>();
        for (MasterRelation relation : relations) {
            list.add(studentDao.findStudentByPhone(relation.getStudentPhone()));
        }
        return list;
    }
}
