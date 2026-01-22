package net.system.mk.backend.aop;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.dao.MerchantConfigMapper;
import net.system.mk.commons.interceptor.BaseWebInterceptor;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.pojo.MerchantConfig;
import net.system.mk.commons.redis.RedisHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author USER
 * @date 2025-03-2025/3/11/0011 18:44
 */
@Component
@Slf4j
public class PermissionInterceptor extends BaseWebInterceptor {

    @Resource
    private AppConfig config;
    @Resource
    private RedisHelper helper;
    @Resource
    private MerchantConfigMapper merchantConfigMapper;


    private static RateLimiter limiter;

    @Override
    protected Integer merchantId(HttpServletRequest request) {
        String serverName = request.getServerName();
        if (serverName.matches("^\\w+-.*$")){
            String mchCode = serverName.split("-")[0];
            MerchantConfig mch = merchantConfigMapper.getByMchCode(mchCode);
            return mch==null?-1:mch.getId();
        }else {
            return -1;
        }
    }

    @Override
    protected boolean traffic() {
        if (limiter==null){
            limiter = RateLimiter.create(config.getQps());
        }
        return limiter.tryAcquire();
    }

    @Override
    protected boolean i18n() {
        return config.getI18n();
    }

    @Override
    protected IBaseContext baseContext(String token) {
        return helper.get(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN,token);
    }

    @Override
    protected boolean validateUri(RequestBaseData ctx) {
        if (ctx.getUri().startsWith("/pub")){
            return true;
        }
        List<String> uris = helper.get(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN_PRIVILEGE,ctx.getToken());
        return uris.contains(ctx.getUri());
    }
}
