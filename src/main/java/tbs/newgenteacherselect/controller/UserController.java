package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.AdminService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.newgenteacherselect.service.UserService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
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


    @Resource
    ApiProxy apiProxy;

    @RequestMapping("login")
    public NetResult login(String phone, String password) throws Exception {

        return apiProxy.method(new IAction<RoleVO>() {
            @Override
            public RoleVO action(NetResult result) throws Exception {
                return userService.login(phone, password);
            }
        }, null);
    }

    @RequestMapping("logout")
    @AccessRequire()
    public NetResult logout() throws Exception {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                userService.logout(result.getInvokeToken());
                return null;
            }
        }, null);
    }

    @RequestMapping("renew")
    @AccessRequire()
    public NetResult renew() throws Exception {
        return apiProxy.method(new IAction<RoleVO>() {
            @Override
            public RoleVO action(NetResult result) throws Exception {
                userService.renew(result.getInvokeToken());
                return null;
            }
        }, null);
    }

    @RequestMapping(value = "studentImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public NetResult importStudent(@RequestBody List<StudentRegisterVO> list) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                studentService.studentImport(list);
                return null;
            }
        }, null);
    }

    @RequestMapping(value = "teacherImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult importTeacher(@RequestBody List<TeacherRegisterVO> list) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                teacherService.saveTeacher(list);
                return null;
            }
        }, null);
    }


    @RequestMapping(value = "adminNew", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult importAdmin(String token, String name, String password, String phone, int department) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                adminService.saveAdmin(token, name, password, phone, department);
                return null;
            }
        }, null);
    }

}
