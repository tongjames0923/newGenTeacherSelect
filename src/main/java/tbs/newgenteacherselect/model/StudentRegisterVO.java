package tbs.newgenteacherselect.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class StudentRegisterVO {
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    String name;
    @ExcelProperty("学号")
    @ColumnWidth(20)
    String number;
    @ExcelProperty("手机号")
    @ColumnWidth(20)
    String phone;
    @ExcelProperty("密码")
    @ColumnWidth(20)
    String password;
    @ExcelProperty("班级")
    @ColumnWidth(20)
    String clas;
    @ExcelProperty("年级")
    @ColumnWidth(20)
    Integer grade;
    @ExcelProperty("部门编号")
    @ColumnWidth(20)
    Long department;
    @ExcelProperty("入学成绩")
    @ColumnWidth(20)
    Double score;

}
