package tbs.newgenteacherselect.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tbs.pojo.ScoreConfigTemplateItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ScoreTemplateVO2 {
    private String templateId;
    private String templateName;
    private DepartmentVO department;
    private String creatorName;
    private String creatorPhone;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;
    private List<ScoreConfigTemplateItem> items;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public DepartmentVO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVO department) {
        this.department = department;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorPhone() {
        return creatorPhone;
    }

    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ScoreConfigTemplateItem> getItems() {
        return items;
    }

    public void setItems(List<ScoreConfigTemplateItem> items) {
        this.items = items;
    }
}
