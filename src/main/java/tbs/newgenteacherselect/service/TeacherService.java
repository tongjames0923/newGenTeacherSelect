package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.utils.BatchUtil;

import java.util.List;

public interface TeacherService {
    void saveTeacher(List<TeacherRegisterVO> teacherRegisterVOS) throws InterruptedException, BatchUtil.SqlExecuteListException;
}
