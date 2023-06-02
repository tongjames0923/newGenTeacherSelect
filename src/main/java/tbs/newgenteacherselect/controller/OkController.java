package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.newgenteacherselect.config.SocketServiceConfig;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.interfaces.IAccess;

import javax.annotation.Resource;

@RestController
public class OkController {


    @RequestMapping("ok")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN})
    public Object ok() throws Exception {
        BaseSessionSocketService.send(SocketServiceConfig.ADMIN_LOG_SERVICE, "invoke ok request!!");
        return "hello world";
    }
}
