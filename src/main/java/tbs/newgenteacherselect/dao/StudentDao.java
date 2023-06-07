package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.utils.sql.impl.SqlUpdateImpl;
import tbs.pojo.Student;
import tbs.pojo.dto.StudentUserDetail;
import tbs.utils.redis.RedisConfig;

import java.util.List;

@Mapper
public interface StudentDao {

    @InsertProvider(type = SqlUpdateImpl.class,method = SqlUpdateImpl.INSERT)
    void saveStudent(Student item);

    @Select(StudentUserDetail.BASIC_DATA_SQL + "where bu.phone=#{phone}")
    @Cacheable(cacheManager = RedisConfig.ShortTermCache, key = "#phone", value = "studentInfo")
    StudentUserDetail findStudentByPhone(String phone);

    @UpdateProvider(type = SqlUpdateImpl.class, method = "update")
    @CacheEvict(key = "#student.phone", value = "studentInfo")
    void updateStudent(Student student);

    @Select("SELECT st.* FROM student st LEFT JOIN basicuser bu ON bu.phone=st.phone LEFT JOIN masterrelation mr ON mr.studentPhone=st.phone WHERE mr.id IS NULL AND bu.departmentId=#{department} order by score desc;")
    List<Student> listDepartmentNoMasterStudentOrderBySocre(int department);

    @Select(StudentUserDetail.BASIC_DATA_SQL + " where bu.departmentId=#{department} limit #{beg},#{end}")
    List<StudentUserDetail> findStudentByDepartment(int department, int beg, int end);

}
