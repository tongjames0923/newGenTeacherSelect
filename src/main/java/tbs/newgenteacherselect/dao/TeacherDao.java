package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.model.TeacherMoreDetail;
import tbs.utils.sql.impl.SqlUpdateImpl;
import tbs.pojo.Teacher;
import tbs.pojo.dto.TeacherDetail;
import tbs.utils.redis.RedisConfig;
import tbs.utils.sql.query.Page;
import tbs.utils.sql.query.Sortable;

import java.util.List;

@Mapper
public interface TeacherDao {
    @InsertProvider(type = SqlUpdateImpl.class, method = "insert")
    void saveTeacher(Teacher teacher);

    @Select("select "+TeacherDetail.BASIC_DATA_SQL + "where bu.phone=#{phone}")
    @Cacheable(cacheManager = RedisConfig.ShortTermCache, key = "#phone", value = "teacherInfo", unless = "#result==null")
    TeacherDetail findTeacherByPhone(String phone);

    @UpdateProvider(type = SqlUpdateImpl.class, method = "update")
    @CacheEvict(key = "#teacher.phone", value = "teacherInfo")
    void updateTeacher(Teacher teacher);


    @Select("select * from teacher t join basicuser b on t.phone = b.phone where departmentId=#{department}")
    List<Teacher> listTeacherByDepartment(Integer department);


    @SelectProvider(type = TeacherQuery.class,method = TeacherQuery.SELECT)
    List<TeacherMoreDetail> findTeacherByQo(TeacherQO qo, Page page, Sortable... sortable);
}
