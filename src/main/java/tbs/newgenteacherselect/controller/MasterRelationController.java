package tbs.newgenteacherselect.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import tbs.framework.process.controller.BaseNetResultController;
import tbs.framework.process.controller.annotation.AccessRequire;
import tbs.framework.process.controller.model.NetResult;
import tbs.framework.process.controller.model.SystemExecutionData;
import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;

import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.MasterRelationService;


import javax.annotation.Resource;

@RequestMapping("coreService")
@RestController
public class MasterRelationController extends BaseNetResultController {


    void checkRightsForSelectOrUnSelect(String teacher, String student, BaseRoleModel invoker) throws NetError {
        if (invoker == null)
            throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "无法读取相关权限");
        if (StringUtils.isEmpty(teacher) || StringUtils.isEmpty(student)) {
            throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "学生/教师不能为空");
        }
        if (invoker.getUserId().equals(teacher) || invoker.getUserId().equals(student) || invoker.getRoleCode() == RoleVO.ROLE_ADMIN)
            return;
        throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "仅支持管理员或被选老师与学生支持此操作");
    }

    @Resource
    MasterRelationService relationService;

    @RequestMapping("select")
    @AccessRequire
    public NetResult select(String student, String master) throws NetError {
        SystemExecutionData data = getRuntime();
        if (tbs.framework.utils.StringUtils.isEmpty(student)) {
            student = data.getInvokeRole().getUserId();
        }
        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        return refresh(relationService.selectMaster(student, master));
    }

    @RequestMapping("unselect")
    @AccessRequire
    public NetResult unselect(String student, String master) throws NetError {
        SystemExecutionData data = getRuntime();
        if (tbs.framework.utils.StringUtils.isEmpty(student)) {
            student = data.getInvokeRole().getUserId();
        }
        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        relationService.returnbackMaster(student, master);
        return refresh();
    }

}
