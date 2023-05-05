package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
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

    @Resource
    ApiProxy proxy;


    @RequestMapping("ok")
    @AsyncReturnFunction
    public NetResult ok() throws Exception {
        return proxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                Thread.sleep(60000);
                return "hello world";
            }
        }, null);
    }

    @Resource
    AsyncMethod asyncMethod;

    @RequestMapping("result/{key}")
    public NetResult getResult(@PathVariable String key) throws Exception {
        return proxy.method(new IAction() {
            @Override
            public Object action(NetResult result) throws Exception {
                return asyncMethod.get(key);
            }
        }, null);
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
