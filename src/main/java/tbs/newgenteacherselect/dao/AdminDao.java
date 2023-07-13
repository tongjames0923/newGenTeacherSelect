package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tbs.framework.annotation.LongTermCache;
import tbs.pojo.Admin;
import tbs.pojo.dto.AdminDetail;

import java.util.List;

@Mapper
public interface AdminDao extends BaseMapper<Admin> {

    @Select(AdminDetail.BASIC_DATA_SQL)
    @LongTermCache(value = "ADMIN_KEY")
    List<AdminDetail> listSu();


    @Select(AdminDetail.BASIC_DATA_SQL + " where adminToken=#{token}")
    @LongTermCache(value = "admin_profile", key = "#token")
    AdminDetail find(String token);
}
