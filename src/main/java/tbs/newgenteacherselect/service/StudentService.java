package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.framework.sql.query.Page;
import tbs.framework.sql.query.Sortable;

import java.util.List;

public interface StudentService {
    void studentImport(List<StudentRegisterVO> vo) throws Exception;

    StudentMoreDetail findStudent(String phone);

    List<StudentMoreDetail> listStudent(StudentQO qo, Page page, Sortable sortable);
}
