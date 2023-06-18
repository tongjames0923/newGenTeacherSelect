package tbs.newgenteacherselect.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.controller.BaseController;
import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.model.SystemExecutionData;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.StudentService;


import javax.annotation.Resource;

@RequestMapping("coreService")
@RestController
public class MasterRelationController extends BaseController {


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
    public Object select(String student, String master) throws NetError {
        SystemExecutionData data = getRuntime();
        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        relationService.selectMaster(student, master);
        return refresh();
    }

    @RequestMapping("unselect")
    @AccessRequire
    public Object unselect(String student, String master) throws NetError {
        SystemExecutionData data = getRuntime();
        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        relationService.returnbackMaster(student, master);
        return refresh();
    }

    @RequestMapping("listMasterRelationStatus")
    public Object listRelationStatus(int configItem, String master) {
        return success(relationService.listStatus(configItem, master));
    }

}
