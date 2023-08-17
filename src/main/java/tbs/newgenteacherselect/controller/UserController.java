package tbs.newgenteacherselect.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.controller.BaseController;
import tbs.framework.controller.BaseNetResultController;
import tbs.framework.controller.annotation.EnhanceMethod;
import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.model.NetResult;
import tbs.framework.model.SystemExecutionData;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.AdminService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.newgenteacherselect.service.UserService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("user")
@RestController
public class UserController extends BaseNetResultController {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @Resource
    StudentService studentService;
    @Resource
    TeacherService teacherService;


    @RequestMapping("login")
    @AccessRequire(limit = false)
    @EnhanceMethod
    public NetResult login(@RequestParam("username") String phone, String password) throws Exception {
        return success(userService.login(phone, password));
    }

    @RequestMapping("logout")
    @AccessRequire()
    @EnhanceMethod
    public NetResult logout() throws Exception {
        SystemExecutionData data = getRuntime();
        userService.logout(data.getInvokeToken());
        return success();
    }


    @RequestMapping("getInfo")
    @AccessRequire
    @EnhanceMethod
    public NetResult getMyInfo() throws NetError {
        return success(userService.getMyInfo(getRuntime().getInvokeRole()));
    }

    @RequestMapping("changePassword")
    @AccessRequire
    public NetResult changePassword(String password) {
        SystemExecutionData data = getRuntime();
        BaseRoleModel roleModel = data.getInvokeRole();
        if (!StringUtils.isEmpty(roleModel.getUserId())) {
            BasicUser basicUser = new BasicUser();
            basicUser.setPhone(roleModel.getUserId());
            basicUser.setPassword(EncryptionTool.encrypt(password));
            userService.updateBaiscInfo(basicUser);
        }
        return success();
    }


    @RequestMapping(value = "changeStudentInfo", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_TEACHER, RoleVO.ROLE_ADMIN})
    public NetResult changeStudent(Student student) {
        userService.updateStudent(student);
        return success();
    }


    @RequestMapping("renew")
    @AccessRequire()
    public NetResult renew() throws Exception {
        SystemExecutionData data = getRuntime();
        userService.renew(data.getInvokeToken());
        return success();
    }

    @RequestMapping(value = "studentImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public NetResult importStudent(@RequestBody List<StudentRegisterVO> list) throws Exception {
        studentService.studentImport(list);
        return success();
    }

    @RequestMapping(value = "teacherImport", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult importTeacher(@RequestBody List<TeacherRegisterVO> list) throws Throwable {

        teacherService.saveTeacher(list);
        return success();
    }


    @RequestMapping(value = "adminNew", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public NetResult importAdmin(String token, String name, String password, String phone, int department) {
        adminService.saveAdmin(token, name, password, phone, department);
        return null;

    }

}
