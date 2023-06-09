package tbs.newgenteacherselect.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.AOP.authorize.model.SystemExecutionData;
import tbs.utils.error.NetError;

import javax.annotation.Resource;

@RequestMapping("coreService")
@RestController
public class MasterRelationController {


    void checkRightsForSelectOrUnSelect(String teacher, String student, BaseRoleModel invoker) throws NetError {
        if (invoker == null)
            throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "无法读取相关权限");
        if (StringUtils.isEmpty(teacher) || StringUtils.isEmpty(student)) {
            throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "学生/教师不能为空");
        }
        if (invoker.getUserId().equals(teacher) || invoker.getUserId().equals(student)||invoker.getRoleCode()==RoleVO.ROLE_ADMIN)
            return;
        throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE, "仅支持管理员或被选老师与学生支持此操作");
    }

    @Resource
    MasterRelationService relationService;

    @RequestMapping("select")
    @AccessRequire
    public Object select(String student, String master, SystemExecutionData data) throws NetError {
        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        return relationService.selectMaster(student, master);
    }

    @RequestMapping("unselect")
    @AccessRequire
    public Object unselect(String student, String master, SystemExecutionData data) throws NetError {

        checkRightsForSelectOrUnSelect(master, student, data.getInvokeRole());
        return relationService.returnbackMaster(student, master);
    }

    @RequestMapping("listMasterRelationStatus")
    public Object listRelationStatus(int configItem, String master) {
        return relationService.listStatus(configItem, master);
    }

}
