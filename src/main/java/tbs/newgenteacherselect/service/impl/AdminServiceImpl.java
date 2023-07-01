package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.dao.AdminDao;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.AdminService;
import tbs.pojo.Admin;
import tbs.pojo.BasicUser;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    BasicUserDao userDao;

    @Resource
    AdminDao adminDao;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveAdmin(String token, String name, String password, String phone, int department) {
        BasicUser basicUser = new BasicUser();
        basicUser.setUid(EncryptionTool.encrypt(phone + "STUDENT!"));
        basicUser.setName(name);
        basicUser.setPassword(EncryptionTool.encrypt(password));
        basicUser.setPhone(phone);
        basicUser.setRole(RoleVO.ROLE_ADMIN);
        basicUser.setDepartmentId(department);
        userDao.save(basicUser);
        Admin admin = new Admin();
        admin.setPhone(phone);
        admin.setAdminToken(token);
        adminDao.insert(admin);
    }
}
