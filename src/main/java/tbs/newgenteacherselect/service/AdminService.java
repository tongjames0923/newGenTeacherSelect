package tbs.newgenteacherselect.service;

import tbs.pojo.BasicUser;

public interface AdminService {
    void saveAdmin(String token, String name, String password, String phone, long
            department);
}
