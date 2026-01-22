package net.system.mk.commons.aop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.PermUserOperationLogMapper;
import net.system.mk.commons.ip2region.IpSearcher;
import net.system.mk.commons.meta.PermLoggedRequest;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.pojo.PermUserOperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author USER
 * @date 2025-11-2025/11/26/0026 10:11
 */
@Component
@Aspect
@Slf4j
@Order(4)
public class PermUserOperationLogAspect {

    @Resource
    private PermUserOperationLogMapper permUserOperationLogMapper;
    @Resource
    private ICtxHelper ctxHelper;
    @Resource
    private IpSearcher ipSearcher;

    @Around("@annotation(ann)")
    public Object around(ProceedingJoinPoint point, PermOperationLogged ann) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        boolean expr = false;
        PermLoggedRequest p = findPermLogRequest(point, method);
        //拿类上的注解
        Class<?> clazz = point.getTarget().getClass();
        Api api = AnnotationUtils.findAnnotation(clazz, Api.class);
        RequestMapping classRequestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
        String gn;
        if (api != null) {
            gn = api.tags()[0];
        } else {
            assert classRequestMapping != null;
            gn = classRequestMapping.value()[0];
        }
        String mn;
        ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
        if (apiOperation != null) {
            mn = apiOperation.value();
        } else {
            mn = method.getName();
        }
        PermUserOperationLog r = new PermUserOperationLog();
        try {
            RequestBaseData rbd = RequestBaseData.getInstance();
            r.setFromIp(rbd.getIp()).setFromRegion(ipSearcher.search(rbd.getIp())).setRequestUri(rbd.getUri());
            IBaseContext ctx = ctxHelper.getBackendCtx();
            r.setPermUserId(ctx.id());
            r.setAction(gn + "@" + mn);
            Object rs = point.proceed();
            return rs;
        } catch (Exception e) {
            expr = true;
            r.setActionContent(e.getMessage());
            throw e;
        } finally {
            if (!expr) {
                if (p != null) {
                    if (ann.detail()) {
                        r.setActionContent("操作前:" + p.getBeforeMemo() + "\n操作后:" + p.getAfterMemo());
                    } else {
                        r.setActionContent(p.getAllMemo());
                    }
                    r.setRelatedNo(String.valueOf(p.getRelatedNo()));
                } else {
                    r.setActionContent("无");
                }
            }
            //log.warn("操作日志：{}", r);
            permUserOperationLogMapper.insert(r);
        }
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
