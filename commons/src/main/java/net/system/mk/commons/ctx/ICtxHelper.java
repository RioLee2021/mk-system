package net.system.mk.commons.ctx;

import cn.hutool.core.util.StrUtil;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.redis.RedisHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author USER
 * @date 2025-11-2025/11/16/0016 8:20
 */
@Component
public class ICtxHelper {

    @Resource
    private RedisHelper helper;

    public String getRequestHeaderToken(){
        return RequestBaseData.getInstance().getToken();
    }

    public IBaseContext getCtxByScopeAndToken(CtxScope scope, String token){
        if (StrUtil.isBlank(token)){
            return null;
        }
        switch (scope){
            case backend:
                return helper.get(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN,token);
            case wap:
                return helper.get(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN,token);
            default:
                return null;
        }
    }

    public String getDeviceTokenByUid(String uid){
        return helper.get(RedisCodeKey.Device.DEVICE_UID_TOKEN,uid);
    }

    public void putDeviceTokenByUid(String uid,String token){
        helper.set(RedisCodeKey.Device.DEVICE_UID_TOKEN,uid,token);
    }

    public void deleteDeviceTokenByUid(String uid){
        helper.delete(RedisCodeKey.Device.DEVICE_UID_TOKEN,uid);
    }

    public void putMemberUidOtp(String otp,String uid){
        helper.setTouch(RedisCodeKey.Common.MEMBER_UID_OTP,uid,otp,3, TimeUnit.MINUTES);
    }

    public String removeMemberUidOtp(String uid){
        String otp =  helper.get(RedisCodeKey.Common.MEMBER_UID_OTP,uid);
        helper.deleteTouch(RedisCodeKey.Common.MEMBER_UID_OTP,uid);
        return otp;
    }

    public IBaseContext getCtx(String pre){
        RequestBaseData rbd = RequestBaseData.getInstance();
        return helper.get(pre, rbd.getToken());
    }

    public IBaseContext getBackendCtx(){
        return getCtx(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN);
    }

    public void kickBackendCtx(String token){
        helper.delete(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN,token);
    }

    public void putBackendCtx(IBaseContext ctx){
        helper.set(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN,ctx.token(),ctx);
    }

    public void kickAllBackendCtx(){
        helper.deleteByPrefix(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN);
    }

    public IBaseContext getWebCtx(){
        return getCtx(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN);
    }



    public void kickWebCtx(String token){
        helper.delete(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN,token);
    }

    public void putWebCtx(IBaseContext ctx){
        helper.set(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN,ctx.token(),ctx);
    }

    public void kickAllWebCtx(){
        helper.deleteByPrefix(RedisCodeKey.Web.WEB_USER_LOGIN_TOKEN);
    }

    public IBaseContext getDeviceCtx(String token){
        return helper.get(RedisCodeKey.Device.DEVICE_LOGIN_TOKEN,token);
    }

    public void kickDeviceCtx(String token){
        helper.delete(RedisCodeKey.Device.DEVICE_LOGIN_TOKEN,token);
    }

    public void kickAllDeviceCtx(){
        helper.deleteByPrefix(RedisCodeKey.Device.DEVICE_LOGIN_TOKEN);
    }

    public void putDeviceCtx(IBaseContext ctx){
        helper.set(RedisCodeKey.Device.DEVICE_LOGIN_TOKEN,ctx.token(),ctx);
    }
}
