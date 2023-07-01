package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tbs.framework.async.AsyncTaskResult;
import tbs.framework.async.ResultStatusor;
import tbs.framework.async.ThreadUtil;
import tbs.framework.interfaces.async.AsyncToDo;
import tbs.framework.sql.BatchUtil;
import tbs.framework.utils.EncryptionTool;
import tbs.framework.utils.StringUtils;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.dao.TeacherDao;
import tbs.newgenteacherselect.dao.TeacherMoredetailDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.TeacherMoreDetail;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.BasicUser;
import tbs.pojo.Teacher;
import tbs.pojo.dto.TeacherDetail;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    @Resource
    ThreadUtil threadUtil;

    @Resource
    TeacherDao teacherDao;

    @Override
    @Transactional
    public void saveTeacher(List<TeacherRegisterVO> vo) throws Throwable {
        List<AsyncToDo> works = new LinkedList<>();
        int pertask = 200;

        int group = vo.size() / pertask + 1;
        List<TeacherRegisterVO>[] groupDatas = new List[group];
        int cnt = 0;
        for (int i = 0; i < group; i++) {
            groupDatas[i] = new LinkedList<>();
            for (int j = 0; j < pertask && cnt < vo.size(); j++) {
                groupDatas[i].add(vo.get(i * pertask + j));
                cnt++;
            }
            final int i1 = i;
            works.add((st -> {
                BatchUtil batchUtil = SpringUtil.getBean(BatchUtil.class);
                for (TeacherRegisterVO s1 : groupDatas[i1]) {
                    batchUtil.batchUpdate(pertask, () -> {
                        Teacher s = new Teacher();
                        s.setPhone(s1.getPhone());
                        s.setPosition(s1.getPosition());
                        s.setPro_title(s1.getPro_title());
                        s.setWorkNo(s1.getWorkNo());
                        batchUtil.getMapper(TeacherDao.class).insert(s);
                    });
                    batchUtil.batchUpdate(pertask, () -> {
                        BasicUser basicUser = new BasicUser();
                        basicUser.setUid(EncryptionTool.encrypt(s1.getPhone() + "TEACHER!"));
                        basicUser.setName(s1.getName());
                        basicUser.setPassword(EncryptionTool.encrypt(s1.getPassword()));
                        basicUser.setDepartmentId(s1.getDepartment());
                        basicUser.setRole(RoleVO.ROLE_TEACHER);
                        basicUser.setPhone(s1.getPhone());
                        batchUtil.getMapper(BasicUserDao.class).save(basicUser);
                    });

                }
                batchUtil.flush();
            }));
        }
        ResultStatusor change = threadUtil.doWithAsync(works).
                execute();
        change.waitForDone();
        if (change.getFailList().size() > 0) {
            for (AsyncTaskResult asyncResult : change.getFailList()) {
                log.error(asyncResult.getException().getMessage(), asyncResult.getException());
            }
            throw change.getFailList().get(0).getException();
        }
    }

    @Override
    public TeacherDetail findTeacher(String teacher) {
        return teacherDao.findTeacherByPhone(teacher);
    }

    @Resource
    TeacherMoredetailDao teacherMoredetailDao;

    @Override
    public IPage<TeacherMoreDetail> findList(TeacherQO qo, Page page) {
        LambdaQueryWrapper<TeacherMoreDetail> teacherMoreDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(qo.getNameOrPhone()))
            teacherMoreDetailLambdaQueryWrapper.and((c) -> {
                c.likeRight(TeacherMoreDetail::getName, qo.getNameOrPhone())
                        .or().likeRight(TeacherMoreDetail::getPhone, qo.getNameOrPhone());
            });
        if (!StringUtils.isEmpty(qo.getPositionOrTitle())) {
            teacherMoreDetailLambdaQueryWrapper.and((c) -> {
                c.likeRight(TeacherMoreDetail::getPosition, qo.getPositionOrTitle())
                        .or().likeRight(TeacherMoreDetail::getPro_title, qo.getPositionOrTitle());
            });
        }
        if (!StringUtils.isEmpty(qo.getDepartment())) {
            teacherMoreDetailLambdaQueryWrapper.and((c) -> {
                c.likeRight(TeacherMoreDetail::getDepartment, qo.getDepartment());
            });
        }
        if (!StringUtils.isNull(qo.getScoreLevel())) {
            teacherMoreDetailLambdaQueryWrapper.and((c) -> {
                c.eq(TeacherMoreDetail::getCnt, qo.getScoreLevel());
            });
        }
        return teacherMoredetailDao.findTeacherByQo(page, teacherMoreDetailLambdaQueryWrapper, qo.getScoreLevel());
    }

//    @Override
//    public List<TeacherMoreDetail> findList(TeacherQO qo, Page page, Sortable... sortables) {
//        return teacherDao.findTeacherByQo(qo,page,sortables);
//    }
}
