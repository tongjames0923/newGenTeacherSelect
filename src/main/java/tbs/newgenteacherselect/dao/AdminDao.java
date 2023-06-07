package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;
import tbs.utils.sql.impl.SqlUpdateImpl;
import tbs.pojo.Admin;
import tbs.pojo.dto.AdminDetail;
import tbs.utils.redis.RedisConfig;

import java.util.List;

@Mapper
public interface AdminDao {

    @Select(AdminDetail.BASIC_DATA_SQL)
    @Cacheable(value = "ADMIN_KEY",unless = "#result==null",cacheManager = RedisConfig.LongTermCache)
    List<AdminDetail> listSu();


    @InsertProvider(type = SqlUpdateImpl.class,method = "insert")
    void saveAdmin(Admin admin);



    @Select(AdminDetail.BASIC_DATA_SQL+" where adminToken=#{token}")
    @Cacheable(value = "admin_profile",key = "#token",unless = "#result==null",cacheManager = RedisConfig.LongTermCache)
    AdminDetail find(String token);
}
