package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.RoleVO;
import tbs.utils.Results.NetResult;

public interface UserService {
    RoleVO login(String phone,String password) throws Exception;

    void logout(String token)throws Exception;

    void renew(String token) throws Exception;
}
