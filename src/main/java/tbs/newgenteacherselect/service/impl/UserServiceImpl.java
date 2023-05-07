package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Component;
import tbs.dao.*;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.UserService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;
import tbs.pojo.dto.AdminDetail;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.EncryptionTool;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {
    @Resource
    RoleDao roleDao;

    @Resource
    StudentDao studentDao;
    @Resource
    TeacherDao teacherDao;

    @Resource
    AdminDao adminDao;

    @Resource
    IAccess access;


    @Resource
    BasicUserDao basicUserDao;

    @Override
    public RoleVO login(String phone, String password) throws Exception {
        password = EncryptionTool.encrypt(password);
        BaseRoleModel baseRole = roleDao.loginRole(phone, password);
        if (baseRole == null)
            throw NetErrorEnum.makeError(NetErrorEnum.LOGIN_FAIL);
        Object obj = null;
        String uuid = phone + "_" + UUID.randomUUID().toString();
        switch (baseRole.getRoleCode()) {
            case 0:
                obj = studentDao.findStudentByPhone(phone);
                break;
            case 1:
                List<AdminDetail> ls = adminDao.listSu();
                for (AdminDetail detail : ls) {
                    if (detail.getPhone().equals(phone)) {
                        uuid = detail.getAdminToken();
                        obj=detail;
                        break;
                    }
                }
                break;
            case 2:
                obj = teacherDao.findTeacherByPhone(phone);
                break;

            default:
                throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);
        }
        baseRole.setUserId(phone);
        access.put(uuid, baseRole);
        return new RoleVO(obj, baseRole, uuid);

    }

    @Override
    public void logout(String token) throws Exception {
        access.deleteToken(token);
    }

    @Override
    public void renew(String token) throws Exception {
        access.put(token, null);
    }

    @Override
    public void updateBaiscInfo(BasicUser basicUser) {
        basicUserDao.updateBaiscUser(basicUser);
    }

    @Override
    public void updateStudent(Student student) {
        studentDao.updateStudent(student);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherDao.updateTeacher(teacher);
    }
}
