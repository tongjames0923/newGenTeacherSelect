package tbs.newgenteacherselect.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ScoreTemplateVO {

    private String templateName;
    private Long department;


    private List<TemplateItem> items = new LinkedList<>();


}
