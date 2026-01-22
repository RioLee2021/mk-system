package net.system.mk.commons.advice;

import net.system.mk.commons.anno.Passed;
import net.system.mk.commons.meta.ResultBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author USER
 * @date 2025-03-2025/3/27/0027 14:30
 */
@ControllerAdvice(annotations = {RestController.class, RestControllerAdvice.class})
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Value("${app.i18n:false}")
    private boolean i18n;


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return !methodParameter.hasMethodAnnotation(Passed.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o==null){
            return ResultBody.success();
        }
        if(o instanceof ResultBody){
            return o;
        }
        return ResultBody.okData(o);
    }
}
