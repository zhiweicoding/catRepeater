package io.github.zhiweicoding.csw;

import com.xpc.easyes.autoconfig.annotation.EsMapperScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("io.github.zhiweicoding.csw.dao.mysql")
@EsMapperScan("io.github.zhiweicoding.csw.dao.es")
@EnableConfigurationProperties
@EnableAspectJAutoProxy
public class CatRepeaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatRepeaterApplication.class, args);
    }

}
