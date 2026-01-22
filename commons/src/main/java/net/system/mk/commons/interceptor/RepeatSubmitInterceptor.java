package net.system.mk.commons.interceptor;

import net.system.mk.commons.anno.RepeatSubmit;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author USER
 * @date 2025-03-2025/3/12/0012 11:28
 */
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit ann = AnnotationUtils.findAnnotation(method, RepeatSubmit.class);
            if (ann!=null){
                if (isRepeatSubmit(request)){
                    throw new GlobalException(GlobalErrorCode.REPEAT_SUBMIT);
                }
            }
        }
        return true;
    }

    /**
     * 判断是否重复提交
     * @param request request
     * @return boolean
     * @throws GlobalException GlobalException
     */
    protected abstract boolean isRepeatSubmit(HttpServletRequest request) throws GlobalException;

}
