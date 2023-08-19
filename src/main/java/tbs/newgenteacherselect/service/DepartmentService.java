package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.DepartmentVO;
import tbs.pojo.Department;

import java.util.List;
import java.util.function.Function;

public interface DepartmentService {
    String fullName(int id) throws Exception;

    void updateDepartmentFullName();

    public DepartmentVO departmentFullNamesMap(long id) throws Exception;

    Department newDepartment(long parent, String name) throws Exception;

    void deleteDepartment(long id) throws Exception;

    Department rename(long id, String name) throws Exception;


    DepartmentVO listAll(long parent) throws Exception;

    <T> void listAllStudentInDepartment(List<T> ls, long dep, Function<Long, List<T>> function) throws Exception;
}
