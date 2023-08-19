package tbs.newgenteacherselect.model;

import lombok.Data;
import lombok.ToString;
import tbs.pojo.Department;

import java.util.LinkedList;
import java.util.List;
@ToString
@Data
public class DepartmentVO {
    private String departmentName;
    private String fullName;
    private long departmentId;
    private List<DepartmentVO> childs=new LinkedList<>();

}
