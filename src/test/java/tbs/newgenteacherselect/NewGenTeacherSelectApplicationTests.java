package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.newgenteacherselect.dao.MasterRelationDao;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.Async.interfaces.ILockProxy;
import tbs.utils.Async.interfaces.ILocker;
import tbs.utils.Results.AsyncTaskResult;
import tbs.utils.error.NetError;
import tbs.utils.sql.query.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {

    @Resource
    StudentService studentService;

    @Test
    void contextLoads() throws InterruptedException, NetError {
        List<StudentMoreDetail> detailList = studentService.listByDepartment(2, new Page(1, 20));
        System.out.println(detailList);
    }

}
