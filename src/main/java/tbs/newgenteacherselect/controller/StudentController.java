package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.controller.BaseController;
import tbs.framework.error.NetError;
import tbs.framework.model.SystemExecutionData;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.utils.sql.query.Page;
import tbs.utils.sql.query.Sortable;

import javax.annotation.Resource;

@RestController
@RequestMapping("student")
public class StudentController extends BaseController {



    @Resource
    MasterRelationService relationService;

    @RequestMapping("myMaster")
    @AccessRequire(manual = {RoleVO.ROLE_STUDENT})
    public Object myMaster() throws NetError {
        SystemExecutionData data=getRuntime();
        return relationService.getMasterByStudent(data.getInvokeRole().getUserId());
    }


    @Resource
    StudentService studentService;

    @RequestMapping("listStudent")
    @AccessRequire(manual = {RoleVO.ROLE_ADMIN, RoleVO.ROLE_TEACHER})
    public Object listStudent(String nameOrPhone, Integer department, Integer level, String Clas, Integer grade,
                              String masterPhone, Integer page, Integer cnt,
                              String sord, String sidx) {
        StudentQO studentQO = new StudentQO();
        studentQO.setNameOrPhone(nameOrPhone);
        studentQO.setClas(Clas);
        studentQO.setDepartment(department);
        studentQO.setLevel(level);
        studentQO.setMasterPhoneOrName(masterPhone);
        studentQO.setGrade(grade);
        Page page1 = new Page(page, cnt);
        Sortable sortable = new Sortable(sord, sidx);
        return success(studentService.listStudent(studentQO, page1, sortable)) ;
    }

}
