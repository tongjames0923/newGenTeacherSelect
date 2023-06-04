package tbs.newgenteacherselect.service;

import org.apache.ibatis.annotations.Select;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.pojo.Student;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentRegisterVO> vo) throws Exception;
}
