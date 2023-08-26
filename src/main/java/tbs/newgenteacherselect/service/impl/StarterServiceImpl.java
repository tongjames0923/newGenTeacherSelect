package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Component;
import tbs.framework.redis.IRedisService;
import tbs.newgenteacherselect.enums.SelectionEnums;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.newgenteacherselect.service.IStarterService;
import tbs.pojo.Department;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class StarterServiceImpl implements IStarterService {

    @Resource
    DepartmentService departmentService;

    @Resource
    IRedisService redisService;

    private static final String PREFIX = "STARTER_KEY:";

    private static String key(int dep) {
        return PREFIX + dep;
    }

    @Override
    public void changeStatus(int dep, SelectionEnums enums, long mins) {
        Department department = departmentService.findOne(dep);
        if (department == null) {
            throw new RuntimeException("不存在的部门id");
        }
        redisService.set(key(dep), enums.getCode(), mins, TimeUnit.MINUTES);
    }

    @Override
    public SelectionEnums getStatus(int dep) {
        String k = key(dep);
        if (!redisService.hasKey(k)) {
            return SelectionEnums.NO_SET;
        }
        return SelectionEnums.convertTo(redisService.get(k, Integer.class));
    }


}
