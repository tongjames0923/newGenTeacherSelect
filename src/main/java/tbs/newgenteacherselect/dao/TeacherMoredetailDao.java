package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tbs.newgenteacherselect.model.TeacherMoreDetail;

import static tbs.framework.constant.Constants.WRAPPER_SQL;

@Mapper
public interface TeacherMoredetailDao extends BaseMapper<TeacherMoreDetail> {
    @Select("select * from ( select "+TeacherMoreDetail.CNT_SQL+","+TeacherMoreDetail.BASIC_DATA_SQL+")T  "+WRAPPER_SQL)
    IPage<TeacherMoreDetail> findTeacherByQo(IPage<TeacherMoreDetail> page, @Param(Constants.WRAPPER) Wrapper wrapper,Integer scoreLevel);
}
