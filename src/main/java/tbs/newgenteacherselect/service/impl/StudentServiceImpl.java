package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.BatchUtil;
import tbs.utils.EncryptionTool;
import tbs.dao.BasicUserDao;
import tbs.dao.StudentDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    ThreadUtil threadUtil;


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
                        studentBatch.getMapper(StudentDao.class).saveStudent(student);
                    });

                } catch (Exception e) {

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
                        basicUser.setUid(EncryptionTool.encrypt("BYPHONE_" + basicUser.getPhone()));
                        userBatch.getMapper(BasicUserDao.class).save(basicUser);
                    });
                } catch (Exception e) {

                }
            }
        });
        threadUtil.doWithAsync(works).
                execute().
                waitForDone();
        userBatch.flush();
        studentBatch.flush();
    }
}
