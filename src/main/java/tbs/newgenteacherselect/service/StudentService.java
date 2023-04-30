package tbs.newgenteacherselect.service;

import tbs.utils.BatchUtil;
import tbs.newgenteacherselect.model.StudentVO;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentVO> vo) throws Exception;
}
