package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.async.annotations.AsyncReturnFunction;
import tbs.framework.controller.BaseNetResultController;
import tbs.framework.controller.annotation.AccessRequire;
import tbs.framework.controller.annotation.EnhanceMethod;
import tbs.framework.controller.model.NetResult;
import tbs.framework.controller.model.SystemExecutionData;
import tbs.framework.error.NetError;
import tbs.framework.model.AsyncDelayResult;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/scoreConfig/*")
public class ScoreConfigController extends BaseNetResultController {

    @Resource
    ScoreConfigService scoreConfigService;

    @RequestMapping(value = "addTemplate", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    @EnhanceMethod
    public NetResult makeTemplate(@RequestBody ScoreTemplateVO data) throws NetError {
        SystemExecutionData executionData = getRuntime();
        scoreConfigService.makeTemplate(data, executionData.getInvokeRole());
        return success();
    }

    @Resource
    SystemExecutionData executionData;

    @RequestMapping("deleteTemplates")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public NetResult deleteTemplate(String template) throws NetError {
        if (scoreConfigService.hasRights(executionData.getInvokeRole(), template))
            scoreConfigService.removeTemplate(template);
        return success();
    }

    @RequestMapping("listScoreConfigTemplates")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public NetResult listDepartmentConfig(long department) throws Exception {
        return success(scoreConfigService.listTemplate(department));
    }


    @RequestMapping("applyConfig")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    @AsyncReturnFunction
    public NetResult applyConfig(String template) throws Exception {
        scoreConfigService.applyConfig(template);
        return success();
    }
}
