package tbs.newgenteacherselect.model;

import lombok.ToString;
import tbs.pojo.Department;

import java.util.LinkedList;
import java.util.List;
@ToString
public class DepartmentVO {
    private String departmentName;
    private String fullName;
    private int departmentId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private List<DepartmentVO> childs=new LinkedList<>();


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<DepartmentVO> getChilds() {
        return childs;
    }

    public void setChilds(List<DepartmentVO> childs) {
        this.childs = childs;
    }
}
