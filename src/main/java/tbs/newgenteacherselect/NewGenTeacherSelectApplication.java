package tbs.newgenteacherselect;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tbs.repo.good.BatchSaveRepositoryImpl;

import javax.persistence.Entity;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableSpringUtil
@EnableAsync
@EnableAspectJAutoProxy
@EnableRabbit
@EntityScan(basePackages = {"tbs.pojo"})
@ComponentScan("tbs")
@EnableJpaRepositories(basePackages = {"tbs.repo"},repositoryBaseClass= BatchSaveRepositoryImpl.class)
public class NewGenTeacherSelectApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewGenTeacherSelectApplication.class, args);
    }

}
