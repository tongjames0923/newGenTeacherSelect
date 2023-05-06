package tbs.newgenteacherselect.service;

import tbs.pojo.Department;

public interface DepartmentService {
    String fullName(int id);

    void updateDepartmentFullName();

    Department newDepartment(int parent,String name)throws Exception;

    void deleteDepartment(int id)throws Exception;

    Department rename(int id,String name)throws Exception;

}
