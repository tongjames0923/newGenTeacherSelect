package tbs.newgenteacherselect;

import cn.hutool.extra.spring.EnableSpringUtil;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
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
import tbs.framework.EnableExcel;
import tbs.framework.EnableTbsBasic;
import tbs.framework.async.annotations.EnableTbsAsync;
import tbs.framework.db.config.EnableBatchUtils;
import tbs.framework.process.EnableTbsProcess;
import tbs.framework.redis.annotations.EnableTbsRedis;


/**
 * @author abstergo
 */
@EnableTransactionManagement
@EnableSpringUtil

@EnableAspectJAutoProxy
@EnableTbsRedis
@EnableTbsProcess
@EnableBatchUtils
@SpringBootApplication
public class NewGenTeacherSelectApplication {

    public static void main(String[] args) {

        SpringApplication.run(NewGenTeacherSelectApplication.class, args);
    }

}
