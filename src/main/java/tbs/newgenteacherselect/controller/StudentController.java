package tbs.newgenteacherselect.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.process.controller.BaseNetResultController;
import tbs.framework.process.controller.annotation.AccessRequire;
import tbs.framework.process.controller.model.NetResult;
import tbs.framework.process.controller.model.SystemExecutionData;
import tbs.framework.db.QueryUtils;
import tbs.framework.error.NetError;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.StudentService;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
public class StudentController extends BaseNetResultController {


    @Resource
    MasterRelationService relationService;
    @Resource
    StudentService studentService;

    @RequestMapping("myMaster")
    @AccessRequire(manual = {RoleVO.ROLE_STUDENT})
    public NetResult myMaster() throws NetError {
        SystemExecutionData data = getRuntime();
        return success(relationService.getMasterByStudent(data.getInvokeRole().getUserId()));
    }


    @RequestMapping("listStudent")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public NetResult listStudent(String nameOrPhone, String department, Integer level, String Clas, Integer grade,
                                 String masterPhone, Integer hasMaster, Integer page, Integer cnt,
                                 String sord, String sidx) {
        StudentQO studentQO = new StudentQO();
        studentQO.setNameOrPhone(nameOrPhone);
        studentQO.setClas(Clas);
        studentQO.setDepartment(department);
        studentQO.setLevel(level);
        studentQO.setMasterPhoneOrName(masterPhone);
        studentQO.setGrade(grade);
        studentQO.setHasMaster(hasMaster);
        Page<StudentMoreDetail> page1 = new Page<>(page, cnt);
        page1.addOrder(QueryUtils.getOrder(sidx, sord));
        return success(studentService.listStudent(studentQO, page1));
    }

}
