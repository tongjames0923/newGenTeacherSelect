package tbs.newgenteacherselect.model;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.ToString;
import tbs.pojo.ScoreConfigTemplateItem;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@ToString
public class ScoreTemplateVO2 {
    private String templateId;
    private String templateName;
    private DepartmentVO department;
    private String creatorName;
    private String creatorPhone;
    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date createDate;
    private List<ScoreConfigTemplateItem> items;

}
