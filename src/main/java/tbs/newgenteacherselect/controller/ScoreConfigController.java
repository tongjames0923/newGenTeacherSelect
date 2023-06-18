package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.annotation.AsyncReturnFunction;
import tbs.framework.controller.BaseController;
import tbs.framework.error.NetError;
import tbs.framework.model.SystemExecutionData;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;


import javax.annotation.Resource;
import java.util.Arrays;

@RestController()
@RequestMapping("scoreConfig")
public class ScoreConfigController extends BaseController {

    @Resource
    ScoreConfigService scoreConfigService;

    @RequestMapping(value = "addTemplate", method = RequestMethod.POST)
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public Object makeTemplate(@RequestBody ScoreTemplateVO data) throws NetError {
        SystemExecutionData executionData = getRuntime();
        scoreConfigService.makeTemplate(data, executionData.getInvokeRole());
        return success();
    }

    @RequestMapping("deleteTemplates")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public Object deleteTemplate(String template, SystemExecutionData data) throws NetError {
        if (scoreConfigService.hasRights(data.getInvokeRole(), template))
            scoreConfigService.removeTemplate(template);
        return success();
    }

    @RequestMapping("listScoreConfigTemplates")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public Object listDepartmentConfig(int department) throws Exception {
        return success(scoreConfigService.listTemplate(department));
    }


    @RequestMapping("applyConfig")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    @AsyncReturnFunction
    public Object applyConfig(String template, int department) throws Exception {
        scoreConfigService.applyConfig(department, template);
        return success();
    }
}
