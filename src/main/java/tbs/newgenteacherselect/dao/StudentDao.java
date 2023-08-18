package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import tbs.newgenteacherselect.model.StudentMoreDetail;

import tbs.pojo.Student;
import tbs.pojo.dto.StudentUserDetail;


import java.util.List;

@Mapper
public interface StudentDao extends BaseMapper<Student> {

//    @InsertProvider(type = SqlUpdateImpl.class,method = SqlUpdateImpl.INSERT)
//    void saveStudent(Student item);

    @Select(StudentUserDetail.BASIC_DATA_SQL + "where bu.phone=#{phone}")
    StudentUserDetail findStudentByPhone(String phone);

//    @UpdateProvider(type = SqlUpdateImpl.class, method = "update")
//    @CacheEvict(key = "#student.phone", value = "studentInfo")
//    void updateStudent(Student student);

    List<Student> listDepartmentNoMasterStudentOrderBySocre(int department);

    List<StudentUserDetail> findStudentByDepartment(int department, int beg, int end);

    StudentMoreDetail findFullDetailByPhone(String phone);

//    List<StudentMoreDetail> listStudentsMoreDetails(StudentQO qo, Page page, Sortable sortable);
}
