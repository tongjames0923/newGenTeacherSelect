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
