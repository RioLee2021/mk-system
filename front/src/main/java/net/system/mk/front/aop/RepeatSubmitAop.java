package net.system.mk.front.aop;

import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.interceptor.RepeatSubmitInterceptor;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.redis.RedisHelper;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author USER
 * @date 2025-03-2025/3/12/0012 11:38
 */
@Component
public class RepeatSubmitAop extends RepeatSubmitInterceptor {

    @Resource
    private AppConfig config;

    @Resource
    private RedisHelper helper;

    @Override
    protected boolean isRepeatSubmit(HttpServletRequest request) throws GlobalException {
        RequestBaseData ctx = RequestBaseData.getInstance();
        String str = OtherUtils.messageFormat("uri:{},dev:{},ip:{},token:{}",ctx.getUri(),ctx.getDevice(),ctx.getIp(),ctx.getToken());
        String keyCode = "repeat_submit:" + str.hashCode();
        if (helper.hasKey(RedisCodeKey.Common.COMMON_REPEAT_SUBMIT,keyCode)) {
            throw new GlobalException(GlobalErrorCode.REPEAT_SUBMIT);
        }
        helper.expire(RedisCodeKey.Common.COMMON_REPEAT_SUBMIT,keyCode,3, TimeUnit.SECONDS);
        return false;
    }
}
