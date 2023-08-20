package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tbs.newgenteacherselect.model.StudentMoreDetail;

import static tbs.framework.constant.Constants.WRAPPER_SQL;
import static tbs.newgenteacherselect.model.StudentMoreDetail.BASE_SQL;

@Mapper
public interface StudentMoreDetailDao extends BaseMapper<StudentMoreDetail> {


    @Select("select * from( select " + BASE_SQL + ")T " + WRAPPER_SQL)
    IPage<StudentMoreDetail> pageStudent(IPage<StudentMoreDetail> page, @Param(Constants.WRAPPER) Wrapper<StudentMoreDetail> wrapper);
}
