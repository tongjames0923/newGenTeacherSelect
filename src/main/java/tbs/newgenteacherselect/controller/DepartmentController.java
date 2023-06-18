package tbs.newgenteacherselect.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tbs.framework.annotation.AccessRequire;
import tbs.framework.controller.BaseController;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.DepartmentService;

@RestController
@RequestMapping("department")
public class DepartmentController extends BaseController {


    @Resource
    DepartmentService departmentService;

    @RequestMapping("fullName")
    @AccessRequire
    public Object fulldepartmentName(int id) throws Exception {
        return success(departmentService.fullName(id));
    }

    @RequestMapping("listDepartment")
    @AccessRequire
    public Object list(int id) throws Exception {
        return success(departmentService.listAll(id));
    }


    @RequestMapping("newDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object newDepartment(String departmentName, int parent) throws Exception {
        return success(departmentService.newDepartment(parent, departmentName)) ;
    }

    @RequestMapping("deleteDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object deleteDepartment(int id) throws Exception {
         departmentService.deleteDepartment(id);
        return success();
    }

    @RequestMapping("changeDepartment")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ChangeDepartment(String departmentName, int id) throws Exception {
        departmentService.rename(id, departmentName);
        return success();
    }


}
