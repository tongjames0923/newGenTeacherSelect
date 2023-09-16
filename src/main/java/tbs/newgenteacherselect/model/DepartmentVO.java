package tbs.newgenteacherselect.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@ToString
@Data
public class DepartmentVO implements Serializable {

    private static final long serialVersionUID = -5250111474705989551L;
    private String departmentName;
    private String fullName;
    private long departmentId;
    private List<DepartmentVO> childs = new LinkedList<>();

}
