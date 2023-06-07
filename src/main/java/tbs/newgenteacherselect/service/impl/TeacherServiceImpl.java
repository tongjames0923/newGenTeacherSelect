package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tbs.newgenteacherselect.dao.BasicUserDao;
import tbs.newgenteacherselect.dao.TeacherDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.BasicUser;
import tbs.pojo.Teacher;
import tbs.pojo.dto.TeacherDetail;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.BatchUtil;
import tbs.utils.EncryptionTool;
import tbs.utils.Results.AsyncTaskResult;
import tbs.utils.Results.AsyncWaitter;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    @Resource
    ThreadUtil threadUtil;

    @Resource
    TeacherDao teacherDao;

    @Override
    @Transactional
    public void saveTeacher(List<TeacherRegisterVO> vo) throws Exception {
        List<AsyncToDo> works = new LinkedList<>();
        int pertask = 200;

        int group = vo.size() / pertask + 1;
        List<TeacherRegisterVO>[] groupDatas = new List[group];
        int cnt = 0;
        for (int i = 0; i < group; i++) {
            groupDatas[i] = new LinkedList<>();
            for (int j = 0; j < pertask && cnt < vo.size(); j++) {
                groupDatas[i].add(vo.get(i * pertask + j));
                cnt++;
            }
            final int i1 = i;
            works.add((st -> {
                BatchUtil batchUtil = SpringUtil.getBean(BatchUtil.class);
                for (TeacherRegisterVO s1 : groupDatas[i1]) {
                    batchUtil.batchUpdate(pertask, () -> {
                        Teacher s = new Teacher();
                        s.setPhone(s1.getPhone());
                        s.setPosition(s1.getPosition());
                        s.setPro_title(s1.getPro_title());
                        s.setWorkNo(s1.getWorkNo());
                        batchUtil.getMapper(TeacherDao.class).saveTeacher(s);
                    });
                    batchUtil.batchUpdate(pertask, () -> {
                        BasicUser basicUser = new BasicUser();
                        basicUser.setUid(EncryptionTool.encrypt(s1.getPhone() + "TEACHER!"));
                        basicUser.setName(s1.getName());
                        basicUser.setPassword(EncryptionTool.encrypt(s1.getPassword()));
                        basicUser.setDepartmentId(s1.getDepartment());
                        basicUser.setRole(RoleVO.ROLE_TEACHER);
                        basicUser.setPhone(s1.getPhone());
                        batchUtil.getMapper(BasicUserDao.class).save(basicUser);
                    });

                }
                batchUtil.flush();
            }));
        }
        AsyncWaitter.ResultStatusor change = threadUtil.doWithAsync(works).
                execute();
        change.waitForDone();
        if (change.getFailList().size() > 0) {
            for (AsyncTaskResult asyncResult : change.getFailList()) {
                log.error(asyncResult.getException().getMessage(), asyncResult.getException());
            }
            throw change.getFailList().get(0).getException();
        }
    }

    @Override
    public TeacherDetail findTeacher(String teacher) {
        return teacherDao.findTeacherByPhone(teacher);
    }
}
