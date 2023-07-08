package tbs.newgenteacherselect.service;

import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;

public interface UserService {
    RoleVO login(String phone, String password) throws Exception;

    Object getMyInfo(BaseRoleModel baseRole) throws NetError;

    void logout(String token) throws Exception;

    void renew(String token) throws Exception;

    void updateBaiscInfo(BasicUser basicUser);

    void updateStudent(Student student);

    void updateTeacher(Teacher teacher);
}
