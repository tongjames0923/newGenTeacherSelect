package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.annotation.AsyncReturnFunction;
import tbs.framework.annotation.LockIt;
import tbs.newgenteacherselect.model.RoleVO;


@RestController
public class OkController {


    @RequestMapping("ok")
    @AsyncReturnFunction
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ok() throws Exception {
        Thread.currentThread().join(1000*60);
        return "hello world";
    }
}
