package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.Utils.Async.AsyncToDo;
import tbs.Utils.Async.AsyncToGet;
import tbs.Utils.Async.IThreadSign;
import tbs.Utils.BatchUtil;
import tbs.Utils.Async.ThreadUtil;
import tbs.dao.BasicUserDao;
import tbs.dao.DepartmentDao;
import tbs.dao.StudentDao;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.Student;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {
    @Resource
    StudentService studentService;

    @Resource
    StudentDao studentDao;

    @Resource
    BasicUserDao basicUserDao;
    @Resource
    BatchUtil batchUtil;

    @Resource
    ThreadUtil threadUtil;

    @Resource
    DepartmentDao departmentDao;

    @Test
    void contextLoads() throws Exception {

        System.out.println(departmentDao.findAllByParent(1));
        System.out.println(departmentDao.findAllByParent(0));
        System.out.println(studentDao.findStudentByDepartment(3));


//        for(StudentDetail s:studentDao.findAllByGrade(1))
//        {
//            System.out.println(s);
//        }


    }

}
