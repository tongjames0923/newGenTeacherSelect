package tbs.newgenteacherselect;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.dao.RoleDao;
import tbs.dao.StudentDao;
import tbs.dao.TeacherDao;
import tbs.newgenteacherselect.model.StudentRegisterVO;
import tbs.newgenteacherselect.model.TeacherRegisterVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.BasicUser;
import tbs.pojo.Teacher;
import tbs.utils.EncryptionTool;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {
    @Resource
    StudentService studentService;

    @Resource
    StudentDao studentDao;
    @Resource
    RoleDao roleDao;

    @Resource
    TeacherService teacherService;

    @Resource
    TeacherDao teacherDao;


    static long beg, end;

    @BeforeAll
    static void before() {
        beg = System.currentTimeMillis();
    }

    @AfterAll
    static void end() {
        end = System.currentTimeMillis();
        System.out.println("cost " + (end - beg) + " ms");
    }

    @Test
    void contextLoads() {
        System.out.println(teacherDao.findTeacherByPhone("111-333-2"));
    }

}
