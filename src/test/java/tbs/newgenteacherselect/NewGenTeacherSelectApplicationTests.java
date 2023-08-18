package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.pojo.Student;


import java.util.function.Consumer;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {





    void apply(Consumer<String> des)
    {
        String value="hello world";
        des.accept(value);
    }


    @Test
    void contextLoads() throws InterruptedException {
        Student student=new Student();
        student.setPhone("123456");
        System.out.println(student);
        apply(student::setPhone);
        System.out.println(student);
    }

}
