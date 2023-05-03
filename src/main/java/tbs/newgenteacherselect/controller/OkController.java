package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class OkController {

    @Resource
    IAccess access;

    @Resource
    StudentDao studentDao;


    @Resource
    RoleDao roleDao;

//    @RequestMapping("ok")
//    @AccessRequire()
//    public NetResult ok(@RequestParam(required = false) BaseRoleModel roleModel) throws Exception {
//        return NetResult.makeResult(() -> {
//            return roleModel;
//        });
//    }
//
//    @RequestMapping("mlogin")
//    public NetResult l(String phone, String password) throws Exception {
//        return NetResult.makeResult(() -> {
//            String uuid = UUID.randomUUID().toString();
//            BaseRoleModel role = roleDao.loginRole(phone, tbs.utils.EncryptionTool.encrypt(password) );
//            if (role != null) {
//                role.setUserId(phone);
//                access.put(uuid, role);
//                return uuid;
//            }
//            return "unlogined";
//        });
//    }
}
