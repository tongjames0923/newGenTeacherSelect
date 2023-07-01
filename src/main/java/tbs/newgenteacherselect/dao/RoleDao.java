package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.cache.annotation.Cacheable;

import tbs.framework.config.RedisConfig;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.sql.SQL_Tool;

import java.util.List;

@Mapper
public interface RoleDao {
    public static final String BASIC_DATA_SQL= "SELECT r.roleid as roleCode,r.rolename as roleName from role r ";
    @Select("SELECT r.roleid as roleCode,r.rolename as roleName FROM basicuser bu JOIN role r ON r.roleid=bu.role WHERE bu.`phone`=#{phone} AND bu.`password`=#{password};")
    @Cacheable(value = "role_user", key = "#phone",unless = "#result==null",cacheManager = RedisConfig.ShortTermCache)
    BaseRoleModel loginRole(String phone, String password);


    @Select(BASIC_DATA_SQL+" where roleid=#{id} ")
    @Cacheable(value = "role",key = "#id",unless = "#result==null",cacheManager = RedisConfig.LongTermCache)
    BaseRoleModel findOne(int id);

    @SelectProvider(type = SQL_Tool.class, method = "rolesIn")
    List<BaseRoleModel> roleInList(List<Integer> ids);
}
