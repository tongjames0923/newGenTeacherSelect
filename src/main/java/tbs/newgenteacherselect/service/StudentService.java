package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.model.StudentRegisterVO;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentRegisterVO> vo) throws Exception;

    StudentMoreDetail findStudent(String phone);

//    List<StudentMoreDetail> listStudent(StudentQO qo, Page page, Sortable sortable);
}
