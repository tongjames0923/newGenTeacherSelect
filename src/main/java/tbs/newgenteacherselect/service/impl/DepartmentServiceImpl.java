package tbs.newgenteacherselect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbs.dao.BasicUserDao;
import tbs.dao.DepartmentDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.pojo.Department;
import tbs.utils.error.NetError;
import tbs.utils.redis.IRedisService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao departmentDao;
    @Resource
    IRedisService redisService;
    @Resource
    BasicUserDao basicUserDao;


    @Override
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    @Async
    public void updateDepartmentFullName() {
        log.debug("定时更新部门全称启动");
        Map<Integer, String> map = new HashMap<>();
        putAllDepartment(map, 0);
        redisService.set("departmentFullName", map);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Department newDepartment(int parent, String name) throws Exception {
        if(parent!=0)
        {
           if(departmentDao.getById(parent)==null) {
               throw  NetErrorEnum.makeError(NetErrorEnum.NO_PARENT);
           }
        }
        Department department = new Department();
        department.setParentId(parent);
        department.setDepartname(name);
        department.setId((int) (UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE));
        departmentDao.save(department.getId(), department.getDepartname(), department.getParentId());
        updateDepartmentFullName();
        return department;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteDepartment(int id) throws Exception {
        if (depHasUser(id))
            throw NetErrorEnum.makeError(NetErrorEnum.HAS_MORE_NODE);
        updateDepartmentFullName();
    }

    boolean depHasUser(int id) {
        boolean s = basicUserDao.findByDepartment(id).size() > 0 ? true : false;
        if (!s) {
            List<Department> child = departmentDao.findAllByParent(id);
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
    public Department rename(int id, String name) throws Exception {
        departmentDao.changeDepartmentName(id, name);
        updateDepartmentFullName();
        return departmentDao.getById(id);
    }

    Map<Integer, String> departmentFullNamesMap() {
        return redisService.get("departmentFullName", Map.class);
    }

    String m_fullName(int id) {
        Department d = departmentDao.getById(id);
        if (d == null) {
            return "";
        }
        if (d.getParentId() == 0) {
            return d.getDepartname();
        }
        return m_fullName(d.getParentId()) + "-" + d.getDepartname();
    }

    void putAllDepartment(Map<Integer, String> src, int id) {

        src.put(id, m_fullName(id));
        List<Department> departmentList = departmentDao.findAllByParent(id);
        for (Department d : departmentList) {
            putAllDepartment(src, d.getId());
        }
    }


    @Override
    public String fullName(int id) {
        return departmentFullNamesMap().getOrDefault(id, "");
    }

}
