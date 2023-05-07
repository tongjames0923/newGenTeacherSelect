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
    DepartmentService departmentService;

    @RequestMapping("fullName")
    @AccessRequire
    public Object fulldepartmentName(int id) throws Exception {
        return departmentService.fullName(id);
    }

    @RequestMapping("listDepartment")
    @AccessRequire
    public Object list(int id) throws Exception {
        return departmentService.listAll(id);
    }


    @RequestMapping("newDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object newDepartment(String departmentName, int parent) throws Exception {
        return departmentService.newDepartment(parent, departmentName);
    }

    @RequestMapping("deleteDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object deleteDepartment(int id) throws Exception {
        departmentService.deleteDepartment(id);
        return null;
    }

    @RequestMapping("changeDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ChangeDepartment(String departmentName, int id) throws Exception {
        return departmentService.rename(id, departmentName);
    }


}
