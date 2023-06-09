package tbs.newgenteacherselect.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.sql.query.Page;
import tbs.utils.sql.query.Sortable;

import javax.annotation.Resource;

@RestController
@RequestMapping("teacher")
public class TeacherController {


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
        return studentService.listStudent(studentQO, page1, sortable);
    }
}