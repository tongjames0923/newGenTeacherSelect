package tbs.newgenteacherselect.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import tbs.framework.redis.constants.RedisConstants;
import tbs.pojo.ScoreConfigTemplate;
import tbs.pojo.ScoreConfigTemplateItem;

import java.util.List;

@Mapper
public interface ScoreConfigDao extends BaseMapper<ScoreConfigTemplate> {


//    @UpdateProvider(type = SqlUpdateImpl.class, method = "insert")
//    void insertTemplate(ScoreConfigTemplate template);
//
//    @UpdateProvider(type = SqlUpdateImpl.class, method = "insert")
//    void insertTemplateItem(ScoreConfigTemplateItem item);

    @Select("select * from scoreconfigtemplate where departmentId=#{department};")
    List<ScoreConfigTemplate> listTemplateByDepartment(long department);

    @Select("select * from scoreconfigtemplate where templateName=#{templateName} and departmentId=#{department} limit 1;")
    ScoreConfigTemplate findOneByDepartmentAndName(long department, String templateName);


    @Select("select * from scoreconfigtemplate where id=#{id}")
    @Cacheable(value = "scoreTemplate",key = "#id",unless = "#result==null" ,cacheManager = RedisConstants.ShortTermCache)
    ScoreConfigTemplate findById(String id);

    @Delete("delete from scoreconfigtemplate where id=#{id}")
    @CacheEvict(value = "scoreTemplate",key = "#id")
    int deleteTemplate(String id);


//    @UpdateProvider(type = SqlUpdateImpl.class,method = SqlUpdateImpl.UPDATE)
//    @CacheEvict(value = "scoreTemplate",key = "#item.id")
//    void updateTemplate(ScoreConfigTemplate item);

    @Delete("delete from scoreconfigtemplateitem where templateId=#{templateId}")
    int deleteTemplateItems(String templateId);

    @Select("select * from scoreconfigtemplateitem where templateId=#{templateId} order by sortCode asc")
    List<ScoreConfigTemplateItem> listTemplateItems(String templateId);

}
