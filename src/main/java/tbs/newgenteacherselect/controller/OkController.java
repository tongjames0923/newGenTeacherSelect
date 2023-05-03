package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tbs.dao.StudentDao;
import tbs.pojo.dto.StudentDetail;
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

    @RequestMapping("ok")
    @AccessRequire()
    public NetResult ok(@RequestParam(required = false) BaseRoleModel roleModel) {
        return NetResult.makeResult(() -> {
            return roleModel;
        });
    }
}
