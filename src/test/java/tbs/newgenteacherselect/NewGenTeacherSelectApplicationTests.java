package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.dao.*;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.utils.Async.interfaces.ILockProxy;
import tbs.utils.Async.interfaces.ILocker;
import tbs.utils.BatchUtil;

import javax.annotation.Resource;

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
    DepartmentDao departmentDao;
    @Resource
    TeacherDao teacherDao;

    @Resource
    ILockProxy lockProxy;


    @Resource
    AdminDao dao;

    @Resource
    BatchUtil batchUtil;
    int num = 0;

    @Test
    void contextLoads() throws InterruptedException {
        Thread[] threads = new Thread[20];
        String lk="LLL";
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    lockProxy.run((ILocker l) -> {
                        for(int j=0;j<10;j++)
                        {
                            System.out.println(num++);
                            try {
                                Thread.currentThread().join(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return null;
                    },lk);
                }
            });
            threads[i].start();
        }
        Thread.currentThread().join(10000);
    }

}
