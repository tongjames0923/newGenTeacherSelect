package tbs.newgenteacherselect;

import cn.hutool.extra.spring.EnableSpringUtil;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tbs.newgenteacherselect.config.RedisThreadLockerProperties;


/**
 * @author abstergo
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableSpringUtil
@EnableAspectJAutoProxy
//@EnableRabbit
@ComponentScan("tbs")
@EnableConfigurationProperties
@EnableScheduling
//@EnableWebSocket
public class NewGenTeacherSelectApplication {

    @Bean
    @ConfigurationProperties(prefix = "tbs.thread.locker.redis")
    RedisThreadLockerProperties redisThreadLockerProperties()
    {
        return new RedisThreadLockerProperties();
    }


    public static void main(String[] args) {

        SpringApplication.run(NewGenTeacherSelectApplication.class, args);
    }

}
