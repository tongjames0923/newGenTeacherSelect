package tbs.newgenteacherselect.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tbs.newgenteacherselect.service.DepartmentService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.Results.NetResult;

@RestController
@RequestMapping("department")
public class DepartmentController {

    @Resource
    ApiProxy apiProxy;

    @Resource
    DepartmentService departmentService;

    @RequestMapping("fullName")
    public NetResult fulldepartmentName(int id) {
        return apiProxy.method((NetResult r) -> {
            return departmentService.fullName(id);
        }, null);
    }

}
