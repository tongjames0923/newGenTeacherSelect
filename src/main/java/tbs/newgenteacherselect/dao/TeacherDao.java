package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.model.TeacherMoreDetail;

import tbs.pojo.Teacher;
import tbs.pojo.dto.TeacherDetail;
import tbs.framework.config.RedisConfig;


import java.util.List;

@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {
//    @InsertProvider(type = SqlUpdateImpl.class, method = "insert")
//    void saveTeacher(Teacher teacher);

    @Select("select "+TeacherDetail.BASIC_DATA_SQL + "where bu.phone=#{phone}")
    @Cacheable(cacheManager = RedisConfig.ShortTermCache, key = "#phone", value = "teacherInfo", unless = "#result==null")
    TeacherDetail findTeacherByPhone(String phone);

//    @UpdateProvider(type = SqlUpdateImpl.class, method = "update")
//    @CacheEvict(key = "#teacher.phone", value = "teacherInfo")
//    void updateTeacher(Teacher teacher);


    @Select("select * from teacher t join basicuser b on t.phone = b.phone where departmentId=#{department}")
    List<Teacher> listTeacherByDepartment(Integer department);



}
