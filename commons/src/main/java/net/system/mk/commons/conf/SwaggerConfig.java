package net.system.mk.commons.conf;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author USER
 */
@Configuration
@EnableSwagger2WebMvc
@Profile("!prod")
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${app.system-version:0}")
    private String version;

    @Bean
    public Docket createRestApi() {
        List<ResponseMessage> resps = new ArrayList<>();
        resps.add(new ResponseMessageBuilder().code(200).message("ok").build());
        // 设置header里面的token
        List<Parameter> pars = new ArrayList<>();
        pars.add(new ParameterBuilder().name("Authorization").description("登录Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        pars.add(new ParameterBuilder().name("Accept-Language").description("语言").modelRef(new ModelRef("string")).parameterType("header").required(false).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(pars)
                .groupName(applicationName)
                .globalResponseMessage(RequestMethod.GET, resps)
                .globalResponseMessage(RequestMethod.POST, resps)
                .apiInfo(new ApiInfoBuilder().title("Swagger（" + applicationName + "）API接口")
                        .version(version).build())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }
}
