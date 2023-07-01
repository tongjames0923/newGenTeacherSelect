package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;
import tbs.framework.config.RedisConfig;
import tbs.pojo.Admin;
import tbs.pojo.dto.AdminDetail;

import java.util.List;

@Mapper
public interface AdminDao extends BaseMapper<Admin> {

    @Select(AdminDetail.BASIC_DATA_SQL)
    @Cacheable(value = "ADMIN_KEY",unless = "#result==null",cacheManager = RedisConfig.LongTermCache)
    List<AdminDetail> listSu();





    @Select(AdminDetail.BASIC_DATA_SQL+" where adminToken=#{token}")
    @Cacheable(value = "admin_profile",key = "#token",unless = "#result==null",cacheManager = RedisConfig.LongTermCache)
    AdminDetail find(String token);
}
