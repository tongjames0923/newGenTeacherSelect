package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import tbs.framework.redis.constants.RedisConstants;
import tbs.pojo.BasicUser;

import java.util.List;

@Mapper
public interface BasicUserDao extends BaseMapper<BasicUser> {
    @Insert("insert into basicuser(uid, name, password, phone, role, departmentId) " +
            "VALUES (#{uid},#{name},#{password},#{phone},#{role},#{departmentId})")
    @Caching(put = {
            @CachePut(value = "base_user",key = "#u.phone",unless = "#u==null",cacheManager = RedisConstants.ShortTermCache)
    },evict = {
            @CacheEvict(value = "dev_users",key = "#u.departmentId")
    })
    int save(BasicUser u);

    @Select("select * from basicuser where departmentId=#{dep} ")
    @Cacheable(value = "dev_users",key = "#dep",cacheManager = RedisConstants.ShortTermCache,unless = "#result==null")
    List<BasicUser> findByDepartment(int dep);


    @Select("select * from basicuser where phone=#{phone} ")
    @Cacheable(value = "base_user",key = "#phone",unless = "#result==null",cacheManager = RedisConstants.ShortTermCache)
    BasicUser findOneByPhone(String phone);


//    @UpdateProvider(type = SqlUpdateImpl.class,method = "update")
//    @Caching(evict = {
//            @CacheEvict(value = "dev_users",allEntries = true)})
//    void updateBaiscUser(BasicUser basicUser);
}
