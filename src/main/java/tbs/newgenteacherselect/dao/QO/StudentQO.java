package tbs.newgenteacherselect.dao.QO;

import lombok.Data;
import tbs.framework.annotation.SqlQueryField;

import static tbs.framework.annotation.SqlQueryField.*;

@Data
public class StudentQO {

    @SqlQueryField(method = RLIKE,map = "name")
    @SqlQueryField(method = RLIKE,map = "phone")
    private String nameOrPhone;
    @SqlQueryField(method = LIKE)
    private String department;
    @SqlQueryField(method = EQ,map = "levelId")
    Integer level;
    @SqlQueryField(method = LIKE)
    String Clas;
    @SqlQueryField(method = EQ)
    Integer grade;
    @SqlQueryField(method = RLIKE,map = "masterName")
    @SqlQueryField(method = RLIKE,map = "masterId")
    String masterPhoneOrName;

    @SqlQueryField(method = EQ)
    Integer hasMaster;
}
