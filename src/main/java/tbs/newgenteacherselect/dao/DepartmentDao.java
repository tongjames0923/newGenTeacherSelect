package tbs.newgenteacherselect.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.framework.redis.constants.RedisConstants;
import tbs.pojo.Department;


import java.util.List;

@Mapper
public interface DepartmentDao {

    @Select("select * from department where parentId=#{id}")
    List<Department> findAllByParent(int id);

    @Select("select p2.* from department p1 join department p2 on p2.id=p1.parentId where p1.id=#{id}")
    Department getParent(int id);

    @Select("select * from department where id=#{i} ")
    @Cacheable(value = "dep_cache", key = "#i", cacheManager = RedisConstants.ShortTermCache, unless = "#result==null")
    Department getById(int i);

    @Update("update department set departname=#{name} where id=#{id}")
    @CacheEvict(value = "dep_cache", key = "#id", cacheManager = RedisConstants.ShortTermCache)
    void changeDepartmentName(int id, String name);


    @Delete("DELETE FROM department WHERE id=#{id};")
    @CacheEvict(value = "dep_cache", key = "#id", cacheManager = RedisConstants.ShortTermCache)
    void deleteDepartment(int id);

    @Insert("insert into department (id, parentId, departname) VALUES (#{id},#{parent},#{name})")
    void save(int id, String name, int parent);

}
