package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.controller.ApiProxy;
import tbs.utils.AOP.controller.IAction;
import tbs.utils.Async.AsyncMethod;
import tbs.utils.Async.annotations.AsyncReturnFunction;
import tbs.utils.Results.NetResult;

import javax.annotation.Resource;

@RestController
public class OkController {

    @Resource
    IAccess access;

    @Resource
    StudentDao studentDao;


    @Resource
    RoleDao roleDao;


    @RequestMapping("ok")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ok() throws Exception {
        return "hello world";
    }


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
