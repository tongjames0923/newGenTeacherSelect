package tbs.newgenteacherselect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Component;
import tbs.framework.config.BeanConfig;
import tbs.framework.interfaces.IAccess;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.*;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.UserService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;
import tbs.pojo.dto.AdminDetail;

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

    IAccess access= BeanConfig.getInstance().getAccessElement();


    @Resource
    BasicUserDao basicUserDao;


    @Override
    public RoleVO login(String phone, String password) throws Exception {
        password = EncryptionTool.encrypt(password);
        BaseRoleModel baseRole = roleDao.loginRole(phone, password);
        if (baseRole == null)
            throw NetErrorEnum.makeError(NetErrorEnum.LOGIN_FAIL);
        Object obj = null;
        String uuid = phone + "_" + UUID.randomUUID();
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
        basicUserDao.save(basicUser);
    }

    @Override
    public void updateStudent(Student student) {
        UpdateWrapper<Student> wrapper=new UpdateWrapper<>();
        wrapper.eq("phone",student.getPhone());
        studentDao.update(student,wrapper);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        UpdateWrapper<Teacher> wrapper=new UpdateWrapper<>();
        wrapper.eq("phone",teacher.getPhone());
        teacherDao.update(teacher,wrapper);
    }
}
