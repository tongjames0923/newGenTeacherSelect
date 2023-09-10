package tbs.newgenteacherselect;

import cn.hutool.extra.spring.EnableSpringUtil;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author abstergo
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableSpringUtil
@EnableAspectJAutoProxy
@EnableKnife4j
@ComponentScan("tbs")
@MapperScan({"tbs.newgenteacherselect.dao","tbs.framework.dao"})
public class NewGenTeacherSelectApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewGenTeacherSelectApplication.class, args);
    }

}
