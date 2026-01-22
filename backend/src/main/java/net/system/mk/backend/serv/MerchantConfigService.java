package net.system.mk.backend.serv;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigAddRequest;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigPagerRequest;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigUpdateRequest;
import net.system.mk.commons.ctx.GlobalCache;
import net.system.mk.commons.dao.MerchantConfigMapper;
import net.system.mk.commons.dao.PermUserMapper;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.expr.GlobalException;
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
 * @date 2025-11-2025/11/28/0028 10:47
 */
@Service
public class MerchantConfigService {

    @Resource
    private MerchantConfigMapper merchantConfigMapper;
    @Resource
    private PermUserMapper permUserMapper;
    @Resource
    private GlobalCache globalCache;

    public PagerResult<MerchantConfig> list(MerchantConfigPagerRequest request) {
        return PagerResult.of(merchantConfigMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void add(MerchantConfigAddRequest request) {
        if (!GoogleAuthenticator.checkCode(request.getOperator().otp(), Long.parseLong(request.getOtp()), DateTimeUtils.nowTimestamp())) {
            throw new GlobalException(BUSINESS_ERROR, "OTP错误");
        }
        MerchantConfig mch = merchantConfigMapper.getByMchCode(request.getMchCode());
        if (mch != null) {
            throw new GlobalException(BUSINESS_ERROR, "商户编号已存在");
        }
        mch = new MerchantConfig();
        BeanUtil.copyProperties(request, mch);
        String secureKey = RandomUtil.randomString(16);
        mch.setCreateBy(request.getOperator().account());
        merchantConfigMapper.insert(mch);
        request.setAllMemo(OtherUtils.fmtString("添加商户：{}", mch));
        //添加一个默认的站长账号，密码为：888888
        PermUser usr = new PermUser();
        usr.setAccount("root@"+mch.getMchCode()).setMerchantId(mch.getId())
                .setRoleType(RoleType.WebMaster).setPassword(MD5.create().digestHex("888888"))
                .setOtpCode(GoogleAuthenticator.getSecretKey());
        usr.setCreateBy(request.getOperator().account());
        permUserMapper.insert(usr);
        globalCache.clearMerchantConfigCache();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void update(MerchantConfigUpdateRequest request) {
        if (!GoogleAuthenticator.checkCode(request.getOperator().otp(), Long.parseLong(request.getOtp()), DateTimeUtils.nowTimestamp())) {
            throw new GlobalException(BUSINESS_ERROR, "OTP错误");
        }
        MerchantConfig mch = merchantConfigMapper.selectById(request.getId());
        if (mch == null){
            throw new GlobalException(BUSINESS_ERROR, "商户不存在");
        }
        request.loadBeforeAndAfter(mch,request);
        BeanUtil.copyProperties(request,mch,"id");
        merchantConfigMapper.updateById(mch);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> changeStatus(OtpUpdateRequest request) {
        if (!GoogleAuthenticator.checkCode(request.getOperator().otp(), Long.parseLong(request.getOtp()), DateTimeUtils.nowTimestamp())) {
            throw new GlobalException(BUSINESS_ERROR, "OTP错误");
        }
        MerchantConfig mch = merchantConfigMapper.selectById(request.getId());
        if (mch == null){
            throw new GlobalException(BUSINESS_ERROR, "商户不存在");
        }
        mch.setDisabled(!mch.getDisabled());
        merchantConfigMapper.updateById(mch);
        request.setAllMemo(OtherUtils.fmtString("修改商户状态为：{}", mch.getDisabled()?"禁用":"启用"));
        return ResultBody.success();
    }
}
