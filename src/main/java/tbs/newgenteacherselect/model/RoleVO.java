package tbs.newgenteacherselect.model;


import lombok.Data;
import lombok.ToString;
import tbs.framework.model.BaseRoleModel;

/**
 * 登录基本数据
 */

@Data
@ToString
public class RoleVO {

    private String token;
    private BaseRoleModel role;
    public static final int ROLE_ADMIN = 1, ROLE_STUDENT = 0, ROLE_TEACHER = 2;

}
