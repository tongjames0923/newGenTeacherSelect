package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.Async.annotations.LockIt;

@RestController
public class OkController {


    @RequestMapping("ok")
    @LockIt
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ok() throws Exception {
        Thread.currentThread().join(2000);
        return "hello world";
    }
}
