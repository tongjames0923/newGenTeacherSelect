package tbs.newgenteacherselect.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.ToString;
import tbs.pojo.ScoreConfigTemplateItem;
import tbs.pojo.StudentLevel;
import tbs.pojo.dto.StudentUserDetail;

@ToString
public class StudentMoreDetail extends StudentUserDetail {

    Integer levelId;
    String levelName;

    String masterName;
    String masterId;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

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
