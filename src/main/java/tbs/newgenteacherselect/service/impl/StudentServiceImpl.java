package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tbs.framework.async.ThreadUtil;
import tbs.framework.interfaces.async.AsyncToDo;
import tbs.framework.sql.BatchUtil;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.dao.StudentLevelDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.MasterRelationService;

import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.StudentDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.utils.sql.query.Page;
import tbs.utils.sql.query.Sortable;

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
        return studentDao.findFullDetailByPhone(phone);
    }



    @Override
    public List<StudentMoreDetail> listStudent(StudentQO qo, Page page, Sortable sortable) {

        return studentDao.listStudentsMoreDetails(qo,page,sortable);
    }
}
