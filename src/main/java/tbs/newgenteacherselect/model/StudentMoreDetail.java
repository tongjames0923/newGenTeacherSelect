package tbs.newgenteacherselect.model;

import cn.hutool.core.bean.BeanUtil;
import tbs.pojo.ScoreConfigTemplateItem;
import tbs.pojo.StudentLevel;
import tbs.pojo.dto.StudentUserDetail;

public class StudentMoreDetail extends StudentUserDetail {

    Integer levelId;
    String levelName;


    public StudentMoreDetail()
    {

    }
    public StudentMoreDetail(StudentUserDetail userDetail, ScoreConfigTemplateItem item) {
        BeanUtil.copyProperties(userDetail,this);
        this.levelId=item.getId();
        this.levelName=item.getConfigName();
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
