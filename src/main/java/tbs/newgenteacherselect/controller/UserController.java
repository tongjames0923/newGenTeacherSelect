package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.UserService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.AOP.controller.IAction;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController {

    @Resource
    UserService userService;

    @Resource
    StudentService studentService;


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
    @AccessRequire(manual = {RoleVO.ROLE_STUDENT,RoleVO.ROLE_TEACHER})
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
    @AccessRequire(manual = {RoleVO.ROLE_STUDENT,RoleVO.ROLE_TEACHER})
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
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult importStudent(List<StudentRegisterVO> list) {
        return apiProxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                studentService.studentImport(list);
                return null;
            }
        }, null);
    }


}
