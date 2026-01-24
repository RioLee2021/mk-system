package net.system.mk.front;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.conf.OptionsTableNameHandler;
import net.system.mk.commons.redis.lock.EnableDistributedLock;
import net.system.mk.commons.serv.InitializationUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author USER
 */
@SpringBootApplication
@ComponentScan({"net.system.mk.*"})
@MapperScan({"net.system.mk.**.dao"})
@Slf4j
@EnableCaching
@EnableDistributedLock
@EnableTransactionManagement(order = 0)
@EnableAspectJAutoProxy(exposeProxy = true)
public class FrontStarter implements CommandLineRunner {

    static {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor page = new PaginationInnerInterceptor(DbType.MYSQL);
        page.setMaxLimit(301L);
        interceptor.addInnerInterceptor(page);
        //动态表名
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new OptionsTableNameHandler());
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        return interceptor;
    }

    public static void main(String... args) {
        SpringApplication.run(FrontStarter.class, args);
    }

    @Resource
    private AppConfig appConfig;
    @Resource
    private InitializationUtils initializationUtils;

    @Override
    public void run(String... args) throws Exception {
        log.warn("当前配置：\n"+appConfig.toLoggerPrintString());
        initializationUtils.loadZoneOffset();

    }
}
