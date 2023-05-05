package tbs.newgenteacherselect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tbs.dao.DepartmentDao;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.pojo.Department;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.redis.IRedisService;
import tbs.utils.redis.RedisConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao departmentDao;
    @Resource
    IRedisService redisService;


    @Override
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    @Async
    public void updateDepartmentFullName() {
        log.debug("定时更新部门全称启动");
        Map<Integer, String> map = new HashMap<>();
        putAllDepartment(map, 0);
        redisService.set("departmentFullName", map);

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
