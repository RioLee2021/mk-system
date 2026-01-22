package net.system.mk.backend.serv;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import net.system.mk.backend.ctrl.system.vo.BatchIdsRequest;
import net.system.mk.backend.ctrl.system.vo.PermUserAddRequest;
import net.system.mk.backend.ctrl.system.vo.PermUserPagerRequest;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.MerchantConfigMapper;
import net.system.mk.commons.dao.PermUserMapper;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.OtpUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MerchantConfig;
import net.system.mk.commons.pojo.PermUser;
import net.system.mk.commons.utils.DateTimeUtils;
import net.system.mk.commons.utils.GoogleAuthenticator;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:57
 */
@Service
public class PermUserService {

    @Resource
    private PermUserMapper permUserMapper;
    @Resource
    private MerchantConfigMapper merchantConfigMapper;
    @Resource
    private ICtxHelper iCtxHelper;

    public PagerResult<PermUser> list(PermUserPagerRequest request) {
        return PagerResult.of(permUserMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void add(PermUserAddRequest request) {
        String mchName = "总平台";
        if (request.getMerchantId()!=0){
            MerchantConfig mch = merchantConfigMapper.selectById(request.getMerchantId());
            if (mch==null) {
                throw new GlobalException(BUSINESS_ERROR, "商户不存在");
            }
            mchName = mch.getMchName();
        }
        if (permUserMapper.getByAccount(request.getAccount())!=null){
            throw new GlobalException(BUSINESS_ERROR, "账号已存在");
        }
        if (request.getRoleType()== RoleType.SuperAdmin&&request.getMerchantId()!=0){
            throw new GlobalException(BUSINESS_ERROR, "超级管理员不能指定商户");
        }
        PermUser usr = new PermUser();
        usr.setMerchantId(request.getMerchantId()).setAccount(request.getAccount()).setPassword(request.getPassword())
                .setRoleType(request.getRoleType());
        String otp = GoogleAuthenticator.getSecretKey();
        usr.setOtpCode(otp);
        permUserMapper.insert(usr);
        request.setAllMemo(OtherUtils.fmtString("添加账号：{}，角色：{}，商户：{}",usr.getAccount(),usr.getRoleType().getChName(),mchName));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<String> resetPassword(OtpUpdateRequest request) {
        PermUser usr = permUserMapper.selectById(request.getId());
        if (usr==null){
            throw new GlobalException(BUSINESS_ERROR, "用户不存在");
        }
        if (!GoogleAuthenticator.checkCode(request.getOperator().otp(),Long.parseLong(request.getOtp()), DateTimeUtils.nowTimestamp())){
            throw new GlobalException(BUSINESS_ERROR, "OTP错误");
        }
        String newPwd = RandomUtil.randomString(8);
        usr.setPassword(MD5.create().digestHex(newPwd));
        permUserMapper.updateById(usr);
        request.setAllMemo(OtherUtils.fmtString("重置密码：{}",usr.getAccount()));
        return ResultBody.okData(newPwd);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<String> resetOtp(OtpUpdateRequest request) {
        PermUser usr = permUserMapper.selectById(request.getId());
        if (usr==null){
            throw new GlobalException(BUSINESS_ERROR, "用户不存在");
        }
        if (!GoogleAuthenticator.checkCode(request.getOperator().otp(),Long.parseLong(request.getOtp()), DateTimeUtils.nowTimestamp())){
            throw new GlobalException(BUSINESS_ERROR, "OTP错误");
        }
        String otp = GoogleAuthenticator.getSecretKey();
        usr.setOtpCode(otp);
        permUserMapper.updateById(usr);
        request.setAllMemo(OtherUtils.fmtString("重置OTP：{}",usr.getAccount()));
        return ResultBody.okData(otp);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> changeStatus(BaseUpdateRequest request) {
        PermUser usr = permUserMapper.selectById(request.getId());
        if (usr==null){
            throw new GlobalException(BUSINESS_ERROR, "用户不存在");
        }
        usr.setDisabled(!usr.getDisabled());
        permUserMapper.updateById(usr);
        request.setAllMemo(OtherUtils.fmtString("设置用户状态为：{}",usr.getDisabled()?"禁用":"启用"));
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> batchKick(BatchIdsRequest request) {
        for (Integer id : request.getIds()){
            PermUser usr = permUserMapper.selectById(id);
            if (StrUtil.isNotBlank(usr.getToken())){
                iCtxHelper.kickBackendCtx(usr.getToken());
                usr.setToken("");
                permUserMapper.updateById(usr);
            }
        }
        return ResultBody.success();
    }
}
