package tbs.newgenteacherselect.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.AOP.controller.IAction;
import tbs.utils.Results.NetResult;

@RestController
@RequestMapping("department")
public class DepartmentController {

    @Resource
    ApiProxy apiProxy;

    @Resource
    DepartmentService departmentService;

    @RequestMapping("fullName")
    @AccessRequire
    public NetResult fulldepartmentName(int id) {
        return apiProxy.method((NetResult r) -> {
            return departmentService.fullName(id);
        }, null);
    }

    @RequestMapping("newDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult newDepartment(String departmentName, int parent) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                return departmentService.newDepartment(parent, departmentName);
            }
        }, null);
    }
    @RequestMapping("deleteDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult deleteDepartment(int id) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                departmentService.deleteDepartment(id);
                return null;
            }
        }, null);
    }
    @RequestMapping("changeDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult ChangeDepartment(String departmentName, int id) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                return departmentService.rename(id,departmentName);
            }
        }, null);
    }




}
