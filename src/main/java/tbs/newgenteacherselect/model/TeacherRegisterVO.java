package tbs.newgenteacherselect.model;

import tbs.pojo.BasicUser;
import tbs.pojo.Teacher;

public class TeacherRegisterVO {

    private Teacher teacher;
    private BasicUser basicUser;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public BasicUser getBasicUser() {
        return basicUser;
    }

    public void setBasicUser(BasicUser basicUser) {
        this.basicUser = basicUser;
    }
}
