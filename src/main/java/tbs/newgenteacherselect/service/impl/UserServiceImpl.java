package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Component;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.dao.TeacherDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.UserService;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.EncryptionTool;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;
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
    IAccess access;


    @Override
    public RoleVO login(String phone, String password) throws Exception {
        password = EncryptionTool.encrypt(password);
        BaseRoleModel baseRole = roleDao.loginRole(phone, password);
        if (baseRole == null)
            throw NetErrorEnum.makeError(NetErrorEnum.LOGIN_FAIL);
        Object obj = null;
        switch (baseRole.getRoleCode()) {
            case 0:
                obj = studentDao.findStudentByPhone(phone);
                break;
            case 1:
                throw NetErrorEnum.makeError(NetErrorEnum.NOT_AVALIABLE);
            case 2:
                obj = teacherDao.findTeacherByPhone(phone);
                break;

            default:
                throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);
        }
        String uuid = phone + "_" + UUID.randomUUID().toString();
        baseRole.setUserId(phone);
        access.put(uuid, baseRole);
        return new RoleVO(obj, baseRole,uuid);

    }

    @Override
    public void logout(String token) throws Exception {
        access.deleteToken(token);
    }

    @Override
    public void renew(String token) throws Exception {
        access.put(token,null);
    }
}
