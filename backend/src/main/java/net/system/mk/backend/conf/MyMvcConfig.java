package net.system.mk.backend.conf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.system.mk.backend.aop.PermissionInterceptor;
import net.system.mk.backend.aop.RepeatSubmitAop;
import net.system.mk.commons.conf.*;
import net.system.mk.commons.utils.CloudflareR2Client;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author USER
 * @date 2025-03-2025/3/11/0011 23:05
 */
@Configuration
@EnableWebMvc
@DependsOn("appConfig")
public class MyMvcConfig implements WebMvcConfigurer {

    @Resource
    private PermissionInterceptor interceptor;
    @Resource
    private RepeatSubmitAop repeatSubmitAop;

    @Bean
    public CloudflareR2Client cloudflareR2Client(){
        CloudflareR2Client.S3Config config = new CloudflareR2Client.S3Config();
        return new CloudflareR2Client(config);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
        registry.addInterceptor(repeatSubmitAop);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return builder -> {
            builder.serializerByType(LocalDateTime.class,new MyLocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class,new MyLocalDateTimeDeserializer());
        };
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter c = new MappingJackson2HttpMessageConverter();
        ObjectMapper om = new ObjectMapper();
        //反序列化失败
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //序列化枚举时使用枚举的索引
        om.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX,true);

        om.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaTimeModule jm = new JavaTimeModule();
        jm.addSerializer(LocalDateTime.class,new MyLocalDateTimeSerializer());
        jm.addDeserializer(LocalDateTime.class,new MyLocalDateTimeDeserializer());
        JavaTimeModule jd = new JavaTimeModule();
        jd.addDeserializer(LocalDate.class,new MyLocalDateDeserializer());
        jd.addSerializer(LocalDate.class,new MyLocalDateSerializer());
        SimpleModule sm = new SimpleModule();
        sm.addSerializer(BigDecimal.class,new MyDecimalSerializer());
        sm.addDeserializer(BigDecimal.class,new MyDecimalDeserializer());
        om.registerModule(sm);
        om.registerModule(jm);
        om.registerModule(jd);
        c.setObjectMapper(om);
        converters.add(c);
    }

    /**
     * 跨域处理
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }
}
