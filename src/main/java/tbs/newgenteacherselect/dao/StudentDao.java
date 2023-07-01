package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.utils.sql.impl.SqlUpdateImpl;
import tbs.pojo.Student;
import tbs.pojo.dto.StudentUserDetail;
import tbs.framework.config.RedisConfig;
import tbs.utils.sql.query.Page;
import tbs.utils.sql.query.Sortable;

import java.util.List;

@Mapper
public interface StudentDao {

    @InsertProvider(type = SqlUpdateImpl.class,method = SqlUpdateImpl.INSERT)
    void saveStudent(Student item);

//    @Select(StudentUserDetail.BASIC_DATA_SQL + "where bu.phone=#{phone}")
    @Cacheable(cacheManager = RedisConfig.ShortTermCache, key = "#phone", value = "studentInfo")
    StudentUserDetail findStudentByPhone(String phone);

    @UpdateProvider(type = SqlUpdateImpl.class, method = "update")
    @CacheEvict(key = "#student.phone", value = "studentInfo")
    void updateStudent(Student student);

    List<Student> listDepartmentNoMasterStudentOrderBySocre(int department);

    List<StudentUserDetail> findStudentByDepartment(int department, int beg, int end);
    StudentMoreDetail findFullDetailByPhone(String phone);

    List<StudentMoreDetail> listStudentsMoreDetails(StudentQO qo, Page page, Sortable sortable);
}
