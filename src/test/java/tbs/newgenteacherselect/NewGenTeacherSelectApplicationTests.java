package tbs.newgenteacherselect;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.utils.BatchUtil;
import tbs.newgenteacherselect.model.StudentVO;
import tbs.newgenteacherselect.service.StudentService;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {
    @Resource
    StudentService studentService;

    static long beg,end;
    @BeforeAll
    static void before()
    {
        beg=System.currentTimeMillis();
    }

    @AfterAll
    static void end()
    {
        end=System.currentTimeMillis();
        System.out.println("cost "+(end-beg)+" ms");
    }

    @Test
    void contextLoads()  {
        List<StudentVO> list=new LinkedList<>();
        for(int i=0;i<100000;i++)
        {
            StudentVO v=new StudentVO();
            v.setClas("class-"+i%100);
            v.setDepartment(3);
            v.setPhone("000-333-"+i);
            v.setName("name_"+i);
            v.setGrade(i%10);
            v.setPassword("pw123456"+i);
            v.setNumber("xh_"+i);
            list.add(v);
        }
        try {
            studentService.studentImport(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
