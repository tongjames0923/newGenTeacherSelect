package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.*;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.AdminService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.newgenteacherselect.service.UserService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.model.SystemExecutionData;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.AOP.controller.IAction;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;


    @RequestMapping("login")
    public Object login(String phone, String password) throws Exception {
        return userService.login(phone, password);
    }

    @RequestMapping("logout")
    @AccessRequire()
    public Object logout(@RequestParam(required = false) SystemExecutionData data) throws Exception {

        userService.logout(data.getInvokeToken());
        return null;

    }

    @RequestMapping("renew")
    @AccessRequire()
    public Object renew(@RequestParam(required = false) SystemExecutionData data) throws Exception {

        userService.renew(data.getInvokeToken());
        return null;

    }

    @RequestMapping(value = "studentImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public Object importStudent(@RequestBody List<StudentRegisterVO> list) throws Exception {

        studentService.studentImport(list);
        return null;

    }

    @RequestMapping(value = "teacherImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object importTeacher(@RequestBody List<TeacherRegisterVO> list) throws Exception {

        teacherService.saveTeacher(list);
        return null;

    }


    @RequestMapping(value = "adminNew", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object importAdmin(String token, String name, String password, String phone, int department) {

        adminService.saveAdmin(token, name, password, phone, department);
        return null;

    }

}
