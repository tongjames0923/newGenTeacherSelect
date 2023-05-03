package tbs.newgenteacherselect.model;

import tbs.utils.AOP.authorize.model.BaseRoleModel;

public class RoleVO<T> {
    T detail;
    BaseRoleModel role;

    String token;
    public static final int ROLE_ADMIN=1,ROLE_STUDENT=0,ROLE_TEACHER=2;

    public RoleVO(T detail, BaseRoleModel role, String token) {
        this.detail = detail;
        this.role = role;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BaseRoleModel getRole() {
        return role;
    }

    public void setRole(BaseRoleModel role) {
        this.role = role;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }
}
