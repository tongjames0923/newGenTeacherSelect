package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.StudentVO;
import tbs.pojo.StudentDetail;

import java.util.List;

public interface StudentService {
    List<StudentDetail> batchRead(List<StudentVO> students);
}
