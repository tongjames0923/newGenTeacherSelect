package tbs.newgenteacherselect.service;

import org.apache.ibatis.annotations.Select;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.pojo.Student;
import tbs.pojo.dto.StudentUserDetail;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentRegisterVO> vo) throws Exception;

    StudentMoreDetail findStudent(String phone);
}
