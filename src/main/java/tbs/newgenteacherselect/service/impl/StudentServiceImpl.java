package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tbs.framework.async.ThreadUtil;
import tbs.framework.interfaces.base.IEmptyParamReturner;
import tbs.framework.sql.BatchUtil;
import tbs.framework.sql.QueryUtils;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.dao.StudentDao;
import tbs.newgenteacherselect.dao.StudentMoreDetailDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Resource
    ThreadUtil threadUtil;

    @Resource
    StudentDao studentDao;


    @Override
    public void studentImport(List<StudentRegisterVO> vo) throws Exception {
        BatchUtil studentBatch = SpringUtil.getBean(BatchUtil.class);
        BatchUtil userBatch = SpringUtil.getBean(BatchUtil.class);
        threadUtil.awaitDo(null, new IEmptyParamReturner<Object>() {
            @Override
            public Object action() throws Throwable {
                for (StudentRegisterVO s : vo) {
                    try {

                        studentBatch.batchUpdate(200, () -> {
                            Student student = new Student();
                            student.setPhone(s.getPhone());
                            student.setGrade(s.getGrade());
                            student.setStudentNo(s.getNumber());
                            student.setCla(s.getClas());
                            student.setScore(s.getScore());
                            studentBatch.getMapper(StudentDao.class).insert(student);
                        });

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
                return null;
            }
        }, new IEmptyParamReturner<Object>() {
            @Override
            public Object action() throws Throwable {
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
                        log.error(e.getMessage(), e);
                    }
                }
                return null;
            }
        });

        userBatch.flush();
        studentBatch.flush();
    }

    @Override
    public StudentMoreDetail findStudent(String phone) {
        return studentDao.findFullDetailByPhone(phone);
    }

    @Resource
    StudentMoreDetailDao studentMoreDetailDao;

    @Override
    public IPage<StudentMoreDetail> listStudent(StudentQO qo, Page page) {

        return studentMoreDetailDao.pageStudent(page, QueryUtils.query(qo));
    }

}
