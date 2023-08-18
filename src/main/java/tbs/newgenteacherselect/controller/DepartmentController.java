package tbs.newgenteacherselect.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import tbs.framework.controller.BaseController;
import tbs.framework.controller.BaseNetResultController;
import tbs.framework.controller.annotation.AccessRequire;
import tbs.framework.controller.model.NetResult;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.DepartmentService;

@RestController
@RequestMapping("department")
public class DepartmentController extends BaseNetResultController {


    @Resource
    DepartmentService departmentService;

    @RequestMapping("fullName")
    @AccessRequire
    public NetResult fulldepartmentName(int id) throws Exception {
        return success(departmentService.fullName(id));
    }

    @RequestMapping("listDepartment")
    @AccessRequire
    public NetResult list(int id) throws Exception {
        return success(departmentService.listAll(id));
    }


    @RequestMapping("newDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult newDepartment(String departmentName, int parent) throws Exception {
        return refresh(departmentService.newDepartment(parent, departmentName));
    }

    @RequestMapping("deleteDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult deleteDepartment(int id) throws Exception {
        departmentService.deleteDepartment(id);
        return refresh();
    }

    @RequestMapping("changeDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult ChangeDepartment(String departmentName, int id) throws Exception {
        departmentService.rename(id, departmentName);
        return refresh();
    }


}
