package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tbs.newgenteacherselect.dao.StudentLevelDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.pojo.ScoreConfigTemplateItem;
import tbs.pojo.dto.StudentUserDetail;
import tbs.pojo.dto.TeacherDetail;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.BatchUtil;
import tbs.utils.EncryptionTool;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.StudentDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.utils.sql.query.Page;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Resource
    ThreadUtil threadUtil;

    @Resource
    StudentDao studentDao;

    @Resource
    StudentLevelDao studentLevelDao;

    @Resource
    MasterRelationService masterRelationService;

    @Override
    public void studentImport(List<StudentRegisterVO> vo) throws Exception {
        List<AsyncToDo> works = new LinkedList<>();
        BatchUtil studentBatch = SpringUtil.getBean(BatchUtil.class);
        BatchUtil userBatch = SpringUtil.getBean(BatchUtil.class);
        works.add((st) -> {

            for (StudentRegisterVO s : vo) {
                try {

                    studentBatch.batchUpdate(200, () -> {
                        Student student = new Student();
                        student.setPhone(s.getPhone());
                        student.setGrade(s.getGrade());
                        student.setStudentNo(s.getNumber());
                        student.setCla(s.getClas());
                        student.setScore(s.getScore());
                        studentBatch.getMapper(StudentDao.class).saveStudent(student);
                    });

                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        });
        works.add((us) -> {
            for (StudentRegisterVO s : vo) {
                try {
                    userBatch.batchUpdate(200, () -> {
                        BasicUser basicUser = new BasicUser();
                        basicUser.setName(s.getName());
                        basicUser.setPassword(EncryptionTool.encrypt(s.getPassword()));
                        basicUser.setPhone(s.getPhone());
                        basicUser.setDepartmentId(s.getDepartment());
                        basicUser.setRole(RoleVO.ROLE_STUDENT);
                        basicUser.setUid(EncryptionTool.encrypt("BYPHONE_" + basicUser.getPhone()));
                        userBatch.getMapper(BasicUserDao.class).save(basicUser);
                    });
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        });
        threadUtil.doWithAsync(works).
                execute().
                waitForDone();
        userBatch.flush();
        studentBatch.flush();
    }

    @Override
    public StudentMoreDetail findStudent(String phone) {
        StudentUserDetail detail= studentDao.findStudentByPhone(phone);
        return translate(detail);
    }



    StudentMoreDetail translate(StudentUserDetail detail)
    {
        if(detail==null)
            return null;
        ScoreConfigTemplateItem level= studentLevelDao.findByPhone(detail.getPhone());
        StudentMoreDetail s=new StudentMoreDetail(detail,level);
        TeacherDetail teacherDetail= masterRelationService.getMasterByStudent(detail.getPhone());
        if(teacherDetail==null)
            return s;
        s.setMasterId(teacherDetail.getPhone());
        s.setMasterName(teacherDetail.getName());
        return s;
    }

    @Override
    public List<StudentMoreDetail> listByDepartment(int department, Page page) {
        int beg=(page.getPage()-1)* page.getCount();
        List<StudentUserDetail> studentUserDetails= studentDao.findStudentByDepartment(department,beg,page.getCount());
        List<StudentMoreDetail> res=new LinkedList<>();
        for(StudentUserDetail studentUserDetail:studentUserDetails)
        {
            res.add(translate(studentUserDetail));
        }
        return res;
    }
}
