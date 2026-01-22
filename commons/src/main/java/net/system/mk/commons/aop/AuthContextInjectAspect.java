package net.system.mk.commons.aop;

import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.PermLoggedRequest;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.redis.RedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author USER
 * @date 2025-11-2025/11/23/0023 8:03
 */
@Component
@Aspect
@Slf4j
@Order(2)
public class AuthContextInjectAspect {

    @Resource
    private RedisHelper helper;

    @Around("@annotation(authCheck)")
    public Object around(ProceedingJoinPoint point, AuthCheck authCheck) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        PermLoggedRequest p = findPermLogRequest(point, method);
        if (p != null){
            IBaseContext ctx = null;
            RequestBaseData rbd = RequestBaseData.getInstance();
            switch (authCheck.ctxScope()){
                case backend:
                    ctx = helper.get(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN,rbd.getToken());
                    break;
                case wap:
                    ctx = helper.get(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN,rbd.getToken());
                    break;
            }
            if (ctx == null){
                throw new GlobalException(GlobalErrorCode.LOGIN_INVALID);
            }
            p.setOperator(ctx);
        }
        return point.proceed();
    }


    private PermLoggedRequest findPermLogRequest(ProceedingJoinPoint point, Method method) {
        Object[] args = point.getArgs();
        Class<?>[] parameterTypes = method.getParameterTypes();

        for (int i = 0; i < parameterTypes.length; i++) {
            // 检查是否为 PermLogRequest 类型或其子类
            if (PermLoggedRequest.class.isAssignableFrom(parameterTypes[i]) && args[i] != null) {
                return (PermLoggedRequest) args[i];
            }
        }
        return null;
    }
}
