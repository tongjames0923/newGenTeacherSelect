package tbs.newgenteacherselect.dao.QO;

import lombok.Data;
import org.springframework.util.StringUtils;
import tbs.pojo.dto.StudentUserDetail;

@Data
public class StudentQO {
    private String nameOrPhone;
    private String department;
    Integer level;
    String Clas;
    Integer grade;
    String masterPhoneOrName;
}
