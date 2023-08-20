package tbs.newgenteacherselect.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.ToString;
import tbs.pojo.ScoreConfigTemplateItem;
import tbs.pojo.StudentLevel;
import tbs.pojo.dto.StudentUserDetail;

@ToString
@Data
public class StudentMoreDetail extends StudentUserDetail {

    public static final String BASE_SQL = "        bu.`name` AS `name`\n" +
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
            ",if(m.masterPhone is null,0,1) as hasMaster " +
            "        FROM basicuser bu\n" +
            "        inner JOIN student st ON st.phone = bu.phone\n" +
            "        inner JOIN role r ON r.roleid = bu.role\n" +
            "        inner JOIN department d ON bu.departmentId = d.id\n" +
            "        left join studentlevel stl on stl.studentPhone = bu.phone\n" +
            "        left join scoreconfigtemplateitem sci on sci.id = stl.levelId\n" +
            "        left join masterrelation m on stl.studentPhone = m.studentPhone\n" +
            "        left join basicuser tch on tch.phone = m.masterPhone ";

    Integer levelId;
    String levelName;

    String masterName;
    String masterId;
    Integer hasMaster;


    public StudentMoreDetail()
    {

    }
    public StudentMoreDetail(StudentUserDetail userDetail, ScoreConfigTemplateItem item) {
        BeanUtil.copyProperties(userDetail,this);
        this.levelId=item.getId();
        this.levelName=item.getConfigName();
    }
}
