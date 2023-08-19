package tbs.newgenteacherselect.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class TeacherRegisterVO {

    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String name;
    @ExcelProperty("工号")
    @ColumnWidth(20)
    private String workNo;
    @ExcelProperty("手机号")
    @ColumnWidth(20)
    private String phone;
    @ExcelProperty("密码")
    @ColumnWidth(20)
    private String password;
    @ExcelProperty("职称")
    @ColumnWidth(20)
    private String pro_title;
    @ExcelProperty("职务")
    @ColumnWidth(20)
    private String position;
    @ExcelProperty("部门编号")
    @ColumnWidth(20)
    private Long department;

}
