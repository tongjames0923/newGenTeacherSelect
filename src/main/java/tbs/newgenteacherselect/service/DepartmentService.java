package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.DepartmentVO;
import tbs.pojo.Department;
import tbs.utils.error.NetError;

public interface DepartmentService {
    String fullName(int id) throws Exception;

    void updateDepartmentFullName();

    Department newDepartment(int parent,String name)throws Exception;

    void deleteDepartment(int id)throws Exception;

    Department rename(int id,String name)throws Exception;


    DepartmentVO listAll(int parent) throws Exception;
}
