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

@Mapper
public interface StudentMoreDetailDao extends BaseMapper<StudentMoreDetail> {
    public static final String BASE_SQL="        bu.`name` AS `name`\n" +
            "        , bu.phone AS `phone`\n" +
            "        , bu.departmentId AS `departmentId`\n" +
            "        , stl.levelId as `levelId`\n" +
            "        , sci.configName as `levelName`\n" +
            "        , m.masterPhone as `masterId`\n" +
            "        , tch.name as `masterName`\n" +
            "        , st.studentNo AS `studentNo`\n" +
            "        , st.cla AS `clas`\n" +
            "        , st.grade AS `grade`\n" +
            "        , r.roleid AS `role`\n" +
            "        , r.rolename AS `roleName`\n" +
            "        , d.departname AS `department`\n" +
            "        , st.score as `score`\n" +
            "        FROM basicuser bu\n" +
            "        inner JOIN student st ON st.phone = bu.phone\n" +
            "        inner JOIN role r ON r.roleid = bu.role\n" +
            "        inner JOIN department d ON bu.departmentId = d.id\n" +
            "        left join studentlevel stl on stl.studentPhone = bu.phone\n" +
            "        left join scoreconfigtemplateitem sci on sci.id = stl.levelId\n" +
            "        left join masterrelation m on stl.studentPhone = m.studentPhone\n" +
            "        left join basicuser tch on tch.phone = m.masterPhone ";

    @Select("select * from( select "+BASE_SQL+")T "+WRAPPER_SQL)
    IPage<StudentMoreDetail> pageStudent(IPage<StudentMoreDetail> page,@Param(Constants.WRAPPER) Wrapper<StudentMoreDetail> wrapper);
}
