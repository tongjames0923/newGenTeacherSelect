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

    @Select("        SELECT st.* " +
            "        FROM student st " +
            "                 LEFT JOIN basicuser bu ON bu.phone = st.phone " +
            "                 LEFT JOIN masterrelation mr ON mr.studentPhone = st.phone " +
            "        WHERE mr.id IS NULL " +
            "          AND bu.departmentId = #{param1} " +
            "        order by score desc")
    List<Student> listDepartmentNoMasterStudentOrderBySocre(long department);


    @Select("select "+StudentMoreDetail.BASE_SQL+"where bu.phone=#{param1}")
    StudentMoreDetail findFullDetailByPhone(String phone);

//    List<StudentMoreDetail> listStudentsMoreDetails(StudentQO qo, Page page, Sortable sortable);
}
