package tbs.newgenteacherselect.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tbs.framework.annotation.ShortTermCache;
import tbs.framework.config.BeanConfig;
import tbs.framework.error.NetError;
import tbs.framework.interfaces.IAccess;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.model.SystemExecutionData;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.CacheConstants;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.config.impl.AccessManager;
import tbs.newgenteacherselect.dao.*;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.UserService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;
import tbs.pojo.dto.AdminDetail;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    IAccess access = BeanConfig.getInstance().getAccessElement();

    @Resource
    HttpServletResponse response;

    @Resource
    BasicUserDao basicUserDao;

    @Resource
    SystemExecutionData executionData;

    @Resource
    HttpServletRequest request;

    @Override
    @ShortTermCache(value = CacheConstants.USER_INFO, key = "#baseRole.userId", unless = "#baseRole.userId==null or #result==null")
    public Object getMyInfo(BaseRoleModel baseRole) throws NetError {
        Object obj = null;
        switch (baseRole.getRoleCode()) {
            case 0:
                obj = studentDao.findStudentByPhone(baseRole.getUserId());
                break;
            case 1:
                List<AdminDetail> ls = adminDao.listSu();
                for (AdminDetail detail : ls) {
                    if (detail.getPhone().equals(baseRole.getUserId())) {
                        obj = detail;
                        break;
                    }
                }
                break;
            case 2:
                obj = teacherDao.findTeacherByPhone(baseRole.getUserId());
                break;

            default:
                throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);
        }
        return obj;
    }


    @Override
    public RoleVO login(String phone, String password) throws Exception {
        password = EncryptionTool.encrypt(password);
        BaseRoleModel baseRole = roleDao.loginRole(phone, password);
        if (baseRole == null)
            throw NetErrorEnum.makeError(NetErrorEnum.LOGIN_FAIL);
        String uuid = phone + "_" + UUID.randomUUID();
        RoleVO roleVO = new RoleVO();
        roleVO.setRole(baseRole);
        roleVO.setToken(uuid);
        roleVO.setRoleNameByLang("ROLE." + baseRole.getRoleName());
        baseRole.setUserId(phone);
        Cookie cookie = new Cookie(executionData.getTokenFrom(), uuid);
        cookie.setPath(request.getContextPath() + "/");
        int exp = AccessManager.getInstance().getLoginActive();
        cookie.setMaxAge(exp);
        response.addCookie(cookie);
        access.put(uuid, baseRole);

        return roleVO;

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
    @CacheEvict(value = CacheConstants.USER_INFO)
    public void updateBaiscInfo(BasicUser basicUser) {
        basicUserDao.save(basicUser);
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_INFO)
    public void updateStudent(Student student) {
        UpdateWrapper<Student> wrapper = new UpdateWrapper<>();
        wrapper.eq("phone", student.getPhone());
        studentDao.update(student, wrapper);
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_INFO)
    public void updateTeacher(Teacher teacher) {
        UpdateWrapper<Teacher> wrapper = new UpdateWrapper<>();
        wrapper.eq("phone", teacher.getPhone());
        teacherDao.update(teacher, wrapper);
    }
}
