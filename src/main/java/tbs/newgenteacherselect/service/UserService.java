package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;

public interface UserService {
    RoleVO login(String phone,String password) throws Exception;

    void logout(String token)throws Exception;

    void renew(String token) throws Exception;

    void updateBaiscInfo(BasicUser basicUser);

    void updateStudent(Student student);
    void updateTeacher(Teacher teacher);
}
