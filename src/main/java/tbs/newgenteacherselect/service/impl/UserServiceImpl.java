package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Component;
import tbs.dao.AdminDao;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.dao.TeacherDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.UserService;
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
}
