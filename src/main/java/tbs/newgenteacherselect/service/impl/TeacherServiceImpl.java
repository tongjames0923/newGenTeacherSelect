package tbs.newgenteacherselect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Component;
import tbs.dao.BasicUserDao;
import tbs.dao.StudentDao;
import tbs.dao.TeacherDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;
import tbs.pojo.Teacher;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.BatchUtil;
import tbs.utils.EncryptionTool;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
public class TeacherServiceImpl implements TeacherService {
    @Resource
    ThreadUtil threadUtil;

    @Override
    public void saveTeacher(List<TeacherRegisterVO> vo) throws InterruptedException, BatchUtil.SqlExecuteListException {
        List<AsyncToDo> works = new LinkedList<>();
        BatchUtil teacherBatch = SpringUtil.getBean(BatchUtil.class);
        BatchUtil userBatch = SpringUtil.getBean(BatchUtil.class);
        works.add((st) -> {

            for (TeacherRegisterVO s1 : vo) {
                try {

                    teacherBatch.batchUpdate(200, () -> {
                        Teacher s = s1.getTeacher();
                        s.setPhone(s1.getBasicUser().getPhone());
                        teacherBatch.getMapper(TeacherDao.class).saveTeacher(s);
                    });
                } catch (Exception e) {

                }
            }
        });
        works.add((us) -> {
            for (TeacherRegisterVO s : vo) {
                try {
                    userBatch.batchUpdate(200, () -> {

                        userBatch.getMapper(BasicUserDao.class).save(s.getBasicUser());
                    });
                } catch (Exception e) {

                }
            }
        });
        threadUtil.doWithAsync(works).
                execute().
                waitForDone();
        userBatch.flush();
        teacherBatch.flush();
    }
}
