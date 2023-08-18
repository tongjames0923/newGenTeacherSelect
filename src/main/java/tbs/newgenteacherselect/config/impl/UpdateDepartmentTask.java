package tbs.newgenteacherselect.config.impl;

import org.springframework.stereotype.Component;

import tbs.framework.xxl.interfaces.IJsonJobHandler;
import tbs.newgenteacherselect.service.DepartmentService;

import javax.annotation.Resource;
import java.util.Map;

@Component("UpdateDepartmentTask")
public class UpdateDepartmentTask implements IJsonJobHandler {
    @Resource
    DepartmentService departmentService;
    @Override
    public String handle(Map params) throws Exception {
        departmentService.updateDepartmentFullName();
        return departmentService.fullName(0);
    }
}
