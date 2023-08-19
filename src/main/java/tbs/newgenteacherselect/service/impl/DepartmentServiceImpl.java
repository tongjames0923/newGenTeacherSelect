package tbs.newgenteacherselect.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbs.framework.redis.IRedisService;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.DepartmentDao;
import tbs.newgenteacherselect.model.DepartmentVO;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.pojo.Department;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao departmentDao;
    @Resource
    IRedisService redisService;
    @Resource
    BasicUserDao basicUserDao;

    private static final String DEPARTMENT_MAP = "department_map";

    @Override
//    @Async
    public void updateDepartmentFullName() {
        log.debug("定时更新部门全称启动");
        DepartmentVO dv = new DepartmentVO();
        dv.setFullName("");
        dv.setDepartmentId(0);
        Map<String, DepartmentVO> map = new HashMap<>();
        putAllDepartment(map, dv);
        redisService.set(DEPARTMENT_MAP, map);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Department newDepartment(long parent, String name) throws Exception {
        if (parent != 0) {
            if (departmentDao.getById(parent) == null) {
                throw NetErrorEnum.makeError(NetErrorEnum.NO_PARENT);
            }
        }
        Department department = new Department();
        department.setParentId(parent);
        department.setDepartname(name);
        department.setId((UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE));
        departmentDao.save(department.getId(), department.getDepartname(), department.getParentId());
        updateDepartmentFullName();
        return department;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteDepartment(long id) throws Exception {
        if (depHasUser(id))
            throw NetErrorEnum.makeError(NetErrorEnum.HAS_MORE_NODE);
        updateDepartmentFullName();
    }

    boolean depHasUser(long id) {
        boolean s = basicUserDao.findByDepartment(id).size() > 0;
        if (!s) {
            List<Department> child = departmentDao.findAllByParent(id);
            departmentDao.deleteDepartment(id);
            for (Department dep : child) {
                if (depHasUser(dep.getId())) {
                    return true;
                } else {
                    departmentDao.deleteDepartment(dep.getId());
                }
            }
        }
        return s;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Department rename(long id, String name) throws Exception {
        departmentDao.changeDepartmentName(id, name);
        updateDepartmentFullName();
        return departmentDao.getById(id);
    }


    @Override
    public DepartmentVO listAll(long parent) throws Exception {
        return departmentFullNamesMap(parent);
    }

    public DepartmentVO departmentFullNamesMap(long id) throws Exception {
        Map mp = redisService.get(DEPARTMENT_MAP, Map.class);
        if (mp == null) {
            updateDepartmentFullName();
            throw new Exception("部门数据字典异常,稍后再试");
        }
        JSONObject obj = (JSONObject) mp.get(String.valueOf(id));
        if (obj == null) {
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND, "不存在" + id + "部门");
        }

        return JSON.toJavaObject(obj, DepartmentVO.class);
    }

    String m_fullName(long id) {
        Department d = departmentDao.getById(id);
        if (d == null) {
            return "";
        }
        if (d.getParentId() == 0) {
            return d.getDepartname();
        }
        return m_fullName(d.getParentId()) + "-" + d.getDepartname();
    }

    void putAllDepartment(Map<String, DepartmentVO> src, DepartmentVO vo) {
        List<Department> childs = departmentDao.findAllByParent(vo.getDepartmentId());
        for (Department d : childs) {
            DepartmentVO dv = new DepartmentVO();
            dv.setFullName(m_fullName(d.getId()));
            dv.setDepartmentId(d.getId());
            dv.setDepartmentName(d.getDepartname());
            putAllDepartment(src, dv);
            vo.getChilds().add(dv);
        }
        src.put(String.valueOf(vo.getDepartmentId()), vo);
    }

    @Override
    public String fullName(int id) throws Exception {
        return departmentFullNamesMap(id).getFullName();
    }

    @Override
    public <T> void listAllStudentInDepartment(List<T> ls, long dep, Function<Long, List<T>> function) throws Exception {
        DepartmentVO departmentVO = this.listAll(dep);
        ls.addAll(function.apply(dep));
        if (CollUtil.isEmpty(departmentVO.getChilds())) {
            return;
        }
        for (DepartmentVO vo : departmentVO.getChilds()) {
            listAllStudentInDepartment(ls, vo.getDepartmentId(), function);
        }
    }
}
