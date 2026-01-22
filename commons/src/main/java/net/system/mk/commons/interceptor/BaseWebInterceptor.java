package net.system.mk.commons.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.RequestBaseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static net.system.mk.commons.expr.GlobalErrorCode.PERMISSION_DENIED;


/**
 * interceptor 拦截器
 */
@Slf4j
public abstract class BaseWebInterceptor implements HandlerInterceptor {


    /**
     * 校验访问控制
     *
     * @return bool
     */
    protected abstract boolean traffic();

    /**
     * 是否国际化
     *
     * @return bool
     */
    protected abstract boolean i18n();

    /**
     * 获取上下文
     *
     * @param token token
     * @return IBaseContext
     */
    protected abstract IBaseContext baseContext(String token);

    /**
     * 校验uri
     *
     * @param ctx 上下文
     * @return bool
     */
    protected abstract boolean validateUri(RequestBaseData ctx);

    /**
     * 获取商户id
     *
     * @param request request
     * @return int
     */
    protected abstract Integer merchantId(HttpServletRequest request);


    protected boolean validate(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            if (!traffic()) {
                throw new GlobalException(GlobalErrorCode.TRAFFIC_LIMIT);
            }
            if (i18n()) {
                i18nHandler(request);
            }
            request.setAttribute(RequestBaseData.MERCHANT_ID, merchantId(request));
            HandlerMethod method = (HandlerMethod) handler;
            AuthCheck ac = AnnotationUtils.findAnnotation(method.getMethod(), AuthCheck.class);
            if (ac != null) {
                RequestBaseData gcp = RequestBaseData.getInstance();
                IBaseContext ctx = baseContext(gcp.getToken());
                if (ctx == null) {
                    throw new GlobalException(GlobalErrorCode.LOGIN_INVALID);
                }
                if (ctx.isBanned()) {
                    throw new GlobalException(GlobalErrorCode.ACCOUNT_DISABLE);
                }
                if (ctx.isRoot()) {
                    return true;
                }
                if (ac.root() && !ctx.isRoot()) {
                    throw new GlobalException(GlobalErrorCode.ROOT_PERMISSION_ONLY);
                }
                if (!validateUri(gcp)) {
                    throw new GlobalException(PERMISSION_DENIED);
                }
            }
        }
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return validate(request, response, handler);
    }

    private void i18nHandler(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");
        Locale locale;
        if (StringUtils.isNotBlank(language)) {
            String[] args = language.split(",");
            locale = Locale.forLanguageTag(args[0]);
        } else {
            locale = Locale.US;
        }
        LocaleContextHolder.setLocale(locale);
    }
}