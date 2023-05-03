package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.StudentRegisterVO;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentRegisterVO> vo) throws Exception;
}
