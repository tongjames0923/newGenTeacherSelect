package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import tbs.Utils.EncryptionTool;
import tbs.newgenteacherselect.model.StudentVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.StudentDetail;
import tbs.repo.BasicUserDao;
import tbs.repo.StudentDao;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {
    @Resource
    StudentService studentService;
    @Resource
    BasicUserDao basicUserDao;
    @Resource
    StudentDao studentDao;

    @Test
    void contextLoads() {
//        List<StudentVO> datas=new ArrayList<>();
//        for(int i=1;i<=10;i++)
//        {
//            StudentVO vo=new StudentVO();
//            vo.setGrade(1);
//            vo.setName("test"+i);
//            vo.setNumber("number"+i);
//            vo.setPassword("testpw"+i);
//            vo.setPhone(i+"");
//            datas.add(vo);
//        }
//        long now=System.currentTimeMillis();
//        List<StudentDetail> details= studentService.batchRead(datas);
//        long done=System.currentTimeMillis();
//        System.out.println("cost "+(done-now)+"ms");
//        StudentDetail detail=studentDao.studentLoginWithPhone("3",EncryptionTool.encrypt("testpw3"));
//        System.out.println(detail);

        for(StudentDetail s:studentDao.findAllByGrade(1))
        {
            System.out.println(s);
        }


    }

}
