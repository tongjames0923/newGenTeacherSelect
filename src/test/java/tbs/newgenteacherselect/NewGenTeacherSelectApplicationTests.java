package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.newgenteacherselect.model.StudentMoreDetail;
import tbs.newgenteacherselect.service.StudentService;
import tbs.utils.error.NetError;
import tbs.utils.sql.query.Page;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {

    @Resource
    StudentService studentService;

    @Test
    void contextLoads() throws InterruptedException, NetError {
    }

}
