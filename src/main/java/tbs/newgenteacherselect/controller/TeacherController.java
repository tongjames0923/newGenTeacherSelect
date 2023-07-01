package tbs.newgenteacherselect.controller;


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.framework.annotation.AccessRequire;
import tbs.framework.controller.BaseController;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.dao.QO.TeacherQO;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;

import javax.annotation.Resource;

@RestController
@RequestMapping("teacher")
public class TeacherController extends BaseController {


    @Resource
    TeacherService teacherService;

    @RequestMapping("listTeacher")
    @AccessRequire
    public Object ListTeacher(String nameOrPhone, String positionOrTitle, Integer department, Integer scoreLevel, Integer page, Integer cnt,
                              String sord, String sidx) {

        TeacherQO teacherQO = new TeacherQO();
        teacherQO.setDepartment(department);
        teacherQO.setNameOrPhone(nameOrPhone);
        teacherQO.setScoreLevel(scoreLevel);
        teacherQO.setPositionOrTitle(positionOrTitle);
//        Page page1 = new Page(page, cnt);
//        Sortable sortable = new Sortable(sord, sidx);
//        return success(teacherService.findList(teacherQO, null));
        return null;
    }
}
