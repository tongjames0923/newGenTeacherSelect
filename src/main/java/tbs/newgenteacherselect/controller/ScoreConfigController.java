package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.model.SystemExecutionData;
import tbs.utils.Results.NetResultCallEnum;
import tbs.utils.error.NetError;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController()
@RequestMapping("scoreConfig")
public class ScoreConfigController {

    @Resource
    ScoreConfigService scoreConfigService;

    @RequestMapping(value = "addTemplate",method = RequestMethod.POST)
    @AccessRequire(manual ={RoleVO.ROLE_ADMIN,RoleVO.ROLE_TEACHER})
    public Object makeTemplate(@RequestBody ScoreTemplateVO data, SystemExecutionData executionData) throws NetError {
        scoreConfigService.makeTemplate(data,executionData.getInvokeRole());
        executionData.setCallbacks(Arrays.asList(NetResultCallEnum.Refresh));
        return null;
    }

}
