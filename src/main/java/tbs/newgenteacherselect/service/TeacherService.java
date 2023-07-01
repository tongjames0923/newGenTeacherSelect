package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.model.TeacherMoreDetail;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.pojo.dto.TeacherDetail;
import tbs.framework.sql.query.Page;
import tbs.framework.sql.query.Sortable;

import java.util.List;

public interface TeacherService {
    void saveTeacher(List<TeacherRegisterVO> teacherRegisterVOS) throws Throwable;
    TeacherDetail findTeacher(String teacher);

    List<TeacherMoreDetail> findList(TeacherQO qo, Page page, Sortable... sortables);
}
