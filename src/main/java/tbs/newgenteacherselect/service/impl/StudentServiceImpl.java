package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tbs.framework.async.ThreadUtil;
import tbs.framework.interfaces.async.AsyncToDo;
import tbs.framework.sql.BatchUtil;
import tbs.framework.utils.EncryptionTool;
import tbs.framework.utils.StringUtils;
import tbs.newgenteacherselect.dao.QO.StudentQO;
import tbs.newgenteacherselect.dao.StudentLevelDao;
import tbs.newgenteacherselect.dao.StudentMoreDetailDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.MasterRelationService;

import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.StudentDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;

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
                        studentBatch.getMapper(StudentDao.class).insert(student);
                    });

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
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
                    log.error(e.getMessage(), e);
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

    @Resource
    StudentMoreDetailDao studentMoreDetailDao;

    @Override
    public IPage<StudentMoreDetail> listStudent(StudentQO qo, Page page) {
        LambdaQueryWrapper<StudentMoreDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(qo.getMasterPhoneOrName())) {
            lambdaQueryWrapper.and((c) -> {
                c.likeRight(StudentMoreDetail::getMasterName, qo.getMasterPhoneOrName())
                        .or().likeRight(StudentMoreDetail::getMasterId, qo.getMasterPhoneOrName());
            });
        }
        if (!StringUtils.isEmpty(qo.getNameOrPhone())) {
            lambdaQueryWrapper.and((c) -> {
                c.likeRight(StudentMoreDetail::getName, qo.getNameOrPhone()).
                        or().likeRight(StudentMoreDetail::getPhone, qo.getNameOrPhone());
            });
        }
        if (!StringUtils.isEmpty(qo.getDepartment())) {
            lambdaQueryWrapper.and((c) -> {
                c.like(StudentMoreDetail::getDepartment, qo.getDepartment());
            });
        }
        if (!StringUtils.isEmpty(qo.getClas())) {
            lambdaQueryWrapper.and((c) -> {
                c.like(StudentMoreDetail::getClas, qo.getClas());
            });
        }

        if (!StringUtils.isNull(qo.getLevel())) {
            lambdaQueryWrapper.and((c) -> {
                c.eq(StudentMoreDetail::getLevelId, qo.getLevel());
            });
        }
        if (!StringUtils.isNull(qo.getGrade())) {
            lambdaQueryWrapper.and((c) -> {
                c.eq(StudentMoreDetail::getGrade, qo.getGrade());
            });
        }
        return studentMoreDetailDao.pageStudent(page, lambdaQueryWrapper);
    }

}
