package net.system.mk.commons.aop;

import net.system.mk.commons.anno.MerchantInject;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.meta.RequestBaseData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author USER
 * @date 2025-04-2025/4/16/0016 7:39
 */
@Aspect
@Component
@Order(1)
public class MerchantInjectAspect {

    @Resource
    private ICtxHelper iCtxHelper;

    @Around("@annotation(merchantInject)")
    public Object around(ProceedingJoinPoint pjp, MerchantInject merchantInject) throws Throwable {
        Object[] args = pjp.getArgs();
        RequestBaseData data = RequestBaseData.getInstance();
        IBaseContext ctx;
        switch (merchantInject.scope()){
            case backend:
                ctx = iCtxHelper.getBackendCtx();
                break;
            case wap:
                ctx = iCtxHelper.getWebCtx();
                break;
            default:
                ctx = null;
                break;
        }
        if(merchantInject.platformPassed()&&data.getMerchantId()==0){
            //如果需要跳过平台账号
            return pjp.proceed();
        }
        for (Object arg : args) {
            if (arg instanceof HttpServletResponse||arg instanceof HttpServletRequest){
                continue;
            }
            BeanWrapper wrapper = new BeanWrapperImpl(arg);
            if (wrapper.isWritableProperty("merchantId")) {
                if (merchantInject.value() > 0) {
                    wrapper.setPropertyValue("merchantId", merchantInject.value());
                } else {
                    wrapper.setPropertyValue("merchantId", merchantInject.fromContext() ? (ctx == null ? -1 : ctx.merchantId()) : data.getMerchantId());
                }
            }
        }
        return pjp.proceed();
    }
}
