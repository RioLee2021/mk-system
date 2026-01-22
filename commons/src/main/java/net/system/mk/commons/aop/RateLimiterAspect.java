package net.system.mk.commons.aop;

import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.anno.RateLimiter;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.LimitType;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.redis.RedisHelper;
import net.system.mk.commons.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author USER
 * @date 2025-03-2025/3/12/0012 11:02
 */
@Component
@Aspect
@Slf4j
public class RateLimiterAspect {

    @Resource
    private RedisHelper helper;

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint point, RateLimiter rateLimiter){
        String key = rateLimiter.key();
        long time  = rateLimiter.time();
        String combineKey = getCombineKey(rateLimiter,point);
        String keyCode = key + combineKey.hashCode();
        Optional<Integer> opt = Optional.ofNullable(helper.get(RedisCodeKey.Common.COMMON_RATE_LIMIT, keyCode));
        int number = opt.orElse(0);
        ++number;
        helper.setTouch(RedisCodeKey.Common.COMMON_RATE_LIMIT, keyCode, number, time, TimeUnit.SECONDS);
        if (number>rateLimiter.count()){
            throw new GlobalException(GlobalErrorCode.TRAFFIC_LIMIT);
        }
    }

    @After("@annotation(rateLimiter)")
    public void after(JoinPoint point, RateLimiter rateLimiter){
        String key = rateLimiter.key();
        String combineKey = getCombineKey(rateLimiter,point);
        String keyCode = key + combineKey.hashCode();
        helper.deleteTouch(RedisCodeKey.Common.COMMON_RATE_LIMIT,keyCode);
    }

    private String getCombineKey(RateLimiter rateLimiter,JoinPoint point){
        RequestBaseData ctx = RequestBaseData.getInstance();
        StringBuilder sb = new StringBuilder(rateLimiter.key());
        if (rateLimiter.type()== LimitType.IP){
            sb.append(":").append(IpUtils.getIp()).append("-");
        }
        if (rateLimiter.type()== LimitType.USER){
            sb.append(":").append(ctx.getToken()).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        sb.append(clazz.getName()).append("-").append(method.getName());
        return sb.toString();
    }
}
