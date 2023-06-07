package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.model.SystemExecutionData;
import tbs.utils.error.NetError;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
public class StudentController {



    @Resource
    MasterRelationService relationService;

    @RequestMapping("myMaster")
    @AccessRequire(manual = {RoleVO.ROLE_STUDENT})
    public Object myMaster(SystemExecutionData data) throws NetError {
        return relationService.getMasterByStudent(data.getInvokeRole().getUserId());
    }


}
