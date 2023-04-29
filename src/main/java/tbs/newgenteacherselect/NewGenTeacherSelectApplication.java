package tbs.newgenteacherselect;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableSpringUtil
@EnableAspectJAutoProxy
@EnableRabbit
@ComponentScan("tbs")
@MapperScan("tbs.dao")
public class NewGenTeacherSelectApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewGenTeacherSelectApplication.class, args);
    }

}
