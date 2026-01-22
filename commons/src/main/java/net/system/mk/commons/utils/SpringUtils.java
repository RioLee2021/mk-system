package net.system.mk.commons.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 项目上下文获取bean
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static Map<String, Object> getBeanWithAnnotation(Class<? extends Annotation> arg) {
        return applicationContext.getBeansWithAnnotation(arg);
    }

    /**
     * get 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }


    /**
     * get 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clz) {
        return getApplicationContext().getBean(clz);
    }


    /**
     * 获取一个接口的所有实现类
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clz) {
        return applicationContext.getBeansOfType(clz);
    }


    /**
     * 获取当前环境（dev test prod）
     */
    public static String activeEnv() {
        return SpringUtils.getApplicationContext().getEnvironment().getActiveProfiles()[0];
    }

}