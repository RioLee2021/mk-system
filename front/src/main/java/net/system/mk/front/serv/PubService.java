package net.system.mk.front.serv;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.MbrAssetsFlwMapper;
import net.system.mk.commons.dao.MbrAssetsMapper;
import net.system.mk.commons.dao.MbrInfoMapper;
import net.system.mk.commons.dao.MerchantConfigMapper;
import net.system.mk.commons.enums.MbrStatus;
import net.system.mk.commons.enums.MbrType;
import net.system.mk.commons.enums.VipLevel;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ip2region.IpSearcher;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrAssets;
import net.system.mk.commons.pojo.MbrInfo;
import net.system.mk.commons.pojo.MerchantConfig;
import net.system.mk.commons.utils.DateTimeUtils;
import net.system.mk.commons.utils.ShareCodeUtils;
import net.system.mk.front.ctrl.vo.MbeRegisterRequest;
import net.system.mk.front.ctrl.vo.MbrLoginRequest;
import net.system.mk.front.ctx.MemberContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 9:24
 */
@Service
public class PubService {

    @Resource
    private MbrInfoMapper mbrInfoMapper;
    @Resource
    private MbrAssetsMapper mbrAssetsMapper;
    @Resource
    private MerchantConfigMapper merchantConfigMapper;
    @Resource
    private IpSearcher ipSearcher;
    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private AppConfig appConfig;
    @Resource
    private MbrAssetsFlwMapper mbrAssetsFlwMapper;

    private void doLogin(MbrInfo mb) {
        MemberContext mc = new MemberContext();
        mc.loadMbrInfo(mb);
        iCtxHelper.putWebCtx(mc);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<String> register(MbeRegisterRequest request) {
        RequestBaseData rbd = RequestBaseData.getInstance();
        if (mbrInfoMapper.existsByUk(request.getPhone()) > 0) {
            throw new GlobalException(BUSINESS_ERROR, "phone number exists");
        }
        MbrInfo p = mbrInfoMapper.getOneByInviteCode(request.getInviteCode());
        if (p == null) {
            throw new GlobalException(BUSINESS_ERROR, "invite code invalid");
        }
        String ca = findCustomerAccount(p.getId());
        String inviteCode = ShareCodeUtils.encodeToCode(System.currentTimeMillis());
        String token = "w#" + IdUtil.fastSimpleUUID();
        if (ca == null) {
            //如果找不到则分配给站长
            MerchantConfig mch = merchantConfigMapper.selectById(p.getMerchantId());
            ca = "root@" + mch.getMchCode();
        }
        MbrInfo mb = new MbrInfo();
        mb.setMerchantId(p.getMerchantId()).setLoginPassword(request.getLoginPassword()).setAccount("A" + request.getPhone())
                .setNickname(request.getNickname()).setPhone(request.getPhone()).setInviteCode(inviteCode).setCurrToken(token)
                .setLastLoginAt(DateTimeUtils.nowDateTime()).setRegisterIp(rbd.getIp()).setRegisterRegion(ipSearcher.search(rbd.getIp()))
                .setLoginIp(rbd.getIp()).setLoginRegion(ipSearcher.search(rbd.getIp())).setStatus(MbrStatus.normal).setParentId(p.getId())
                .setParentId(p.getId()).setVipLevel(VipLevel.member).setMbrType(MbrType.un_bind_bank_card).setVirtualFlg(Boolean.FALSE)
                .setDailyOrderLimit(0).setCustomerAccount(ca);
        mbrInfoMapper.insert(mb);
        String route = p.getRelationshipRoute() + "," + mb.getId();
        mb.setRelationshipRoute(route);
        mbrInfoMapper.updateById(mb);
        MbrAssets ma = new MbrAssets();
        ma.setMbrId(mb.getId());
        mbrAssetsMapper.insert(ma);
        doLogin(mb);
        return ResultBody.okData(token);
    }

    private String findCustomerAccount(int mbrId) {
        MbrInfo mb = mbrInfoMapper.selectById(mbrId);
        if (mb == null) {
            return null;
        }
        if (StrUtil.isBlank(mb.getCustomerAccount())) {
            return findCustomerAccount(mb.getParentId());
        }
        return mb.getCustomerAccount();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<String> login(MbrLoginRequest request) {
        MbrInfo mb = mbrInfoMapper.getOneByUk(request.getPhone());
        if (mb == null || !mb.getLoginPassword().equals(request.getPassword())) {
            throw new GlobalException(BUSINESS_ERROR, "phone number or password invalid");
        }
        if (mb.getStatus() == MbrStatus.banned) {
            throw new GlobalException(BUSINESS_ERROR, "account has been banned");
        }
        String token = "w#" + IdUtil.fastSimpleUUID();
        if (StrUtil.isNotBlank(mb.getCurrToken())) {
            iCtxHelper.kickWebCtx(mb.getCurrToken());
        }
        mb.setCurrToken(token);
        RequestBaseData rbd = RequestBaseData.getInstance();
        mb.setLastLoginAt(DateTimeUtils.nowDateTime()).setLoginIp(rbd.getIp()).setLoginRegion(ipSearcher.search(rbd.getIp()));
        doLogin(mb);
        mbrInfoMapper.updateById(mb);
        return ResultBody.okData(token);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> logout() {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        MbrInfo mb = mbrInfoMapper.selectById(ctx.id());
        iCtxHelper.kickWebCtx(ctx.token());
        if (mb != null) {
            mb.setCurrToken(null);
            mbrInfoMapper.updateById(mb);
        }
        return ResultBody.success();
    }

    public ResultBody<MemberContext> info() {
        MemberContext ctx = (MemberContext) iCtxHelper.getWebCtx();
        ctx.loadMbrAssets(mbrAssetsMapper.selectById(ctx.id()));
        ZoneId zid = ZoneId.of(appConfig.getZoneId());
        LocalDate today = LocalDateTime.now(zid).toLocalDate();
        ctx.setTodayEarnings(mbrAssetsFlwMapper.sumAmountByMbrIdAndStartTime(ctx.id(), today.atStartOfDay()));
        ctx.setTotalRevenue(mbrAssetsFlwMapper.sumAmountByMbrId(ctx.id()));
        return ResultBody.okData(ctx);
    }
}
