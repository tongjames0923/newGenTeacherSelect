package tbs.newgenteacherselect.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.model.TeacherMoreDetail;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.pojo.dto.TeacherDetail;


import java.util.List;

public interface TeacherService {
    void saveTeacher(List<TeacherRegisterVO> teacherRegisterVOS) throws Throwable;
    TeacherDetail findTeacher(String teacher);

    IPage<TeacherMoreDetail> findList(TeacherQO qo, Page page);
}
