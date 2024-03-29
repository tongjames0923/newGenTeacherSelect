package tbs.newgenteacherselect.model;




import lombok.Data;
import lombok.ToString;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.translate.annotation.TranlateField;
import tbs.framework.translate.interfaces.impl.LocalStringTranslateImpl;

/**
 * 登录基本数据
 */

@Data
@ToString
public class RoleVO {

    private String token;
    private BaseRoleModel role;
    @TranlateField(LocalStringTranslateImpl.class)
    private String roleNameByLang;
    public static final int ROLE_ADMIN = 1, ROLE_STUDENT = 0, ROLE_TEACHER = 2;

}
