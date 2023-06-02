package tbs.newgenteacherselect.model;

import java.util.List;

public class ScoreTemplateVO {
    public static class Item
    {
        String name;
        int index;
        Integer percent;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Integer getPercent() {
            return percent;
        }

        public void setPercent(Integer percent) {
            this.percent = percent;
        }
    }
    private String templateName;
    private Integer department;

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private List<Item> items;


}
