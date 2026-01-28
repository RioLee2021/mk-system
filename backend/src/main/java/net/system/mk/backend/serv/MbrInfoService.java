package net.system.mk.backend.serv;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.biz.vo.*;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.*;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.enums.MbrStatus;
import net.system.mk.commons.enums.MbrType;
import net.system.mk.commons.enums.RecordStatus;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ext.CustomerChatDetail;
import net.system.mk.commons.ip2region.IpSearcher;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.RequestBaseData;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.*;
import net.system.mk.commons.serv.MbrAssetHelper;
import net.system.mk.commons.utils.OtherUtils;
import net.system.mk.commons.utils.ShareCodeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:27
 */
@Service
public class MbrInfoService {

    @Resource
    private MbrInfoMapper mbrInfoMapper;
    @Resource
    private IpSearcher ipSearcher;
    @Resource
    private MbrAssetsMapper  mbrAssetsMapper;
    @Resource
    private MbrRechargeRecordMapper mbrRechargeRecordMapper;
    @Resource
    private MbrWithdrawRecordMapper mbrWithdrawRecordMapper;
    @Resource
    private MbrAssetHelper mbrAssetHelper;
    @Resource
    private CustomerChatMapper customerChatMapper;
    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private CustomerChatService  customerChatService;

    public PagerResult<MbrInfo> list(MbrInfoPagerRequest request) {
        QueryWrapper<Object> q = OtherUtils.createIdDescWrapper(request, "mb");
        if (request.getCond() != null) {
            q.and(oc ->
                    oc.eq("mb.phone", request.getCond())
                            .or().eq("mb.invite_code", request.getCond())
                            .or().eq("mb.id", request.getCond()).or().eq("mb.account", request.getCond())
            );
        }
        if (StrUtil.isNotBlank(request.getIp())) {
            q.and(orCond -> orCond.eq("mb.register_ip", request.getIp()).or().eq("mb.login_ip", request.getIp()));
        }
        if (request.getSameIpOnly()) {
            q.and(oc -> oc.gt("login_stat.cnt", 1).or().gt("reg_stat.cnt", 1));
        }
        q.groupBy("mb.id");
        return PagerResult.of(mbrInfoMapper.getPageByEw(request.toPage(), q));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void add(MbrInfoSaveRequest request) {
        RequestBaseData rbd = RequestBaseData.getInstance();
        //当上级ID不为0时检查上级ID是否合法
        MbrInfo pm = mbrInfoMapper.selectById(request.getParentId());
        if (request.getParentId() != 0 && pm == null) {
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "上级不存在");
        }
        if (mbrInfoMapper.exists(new QueryWrapper<MbrInfo>().eq("phone", request.getPhone()))) {
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "手机号已存在");
        }
        if (mbrInfoMapper.exists(new QueryWrapper<MbrInfo>().eq("account", "A" + request.getPhone()))) {
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "账号已存在");
        }
        String inviteCode = createShareCode();
        MbrInfo mb = new MbrInfo();
        BeanUtil.copyProperties(request, mb);
        mb.setInviteCode(inviteCode).setAccount("A" + request.getPhone());
        //注册IP
        mb.setRegisterIp(rbd.getIp()).setRegisterRegion(ipSearcher.search(rbd.getIp())).setStatus(MbrStatus.normal).setMbrType(MbrType.un_login)
                .setCustomerAccount(request.getOperator().account());
        mbrInfoMapper.insert(mb);
        //更新关系路由
        String ship = pm == null ? mb.getId() + "" : pm.getRelationshipRoute()  + "," + mb.getId();
        mb.setRelationshipRoute(ship);
        mbrInfoMapper.updateById(mb);
        //创建资产表
        MbrAssets mba = new MbrAssets();
        mba.setMbrId(mb.getId());
        mbrAssetsMapper.insert(mba);
        request.setAllMemo(OtherUtils.fmtString("添加会员:{}", request));
    }

    private String createShareCode(){
        List<String> used = mbrInfoMapper.getUsedInviteCodes();
        String rs = RandomUtil.randomNumbers(8);
        while (!used.contains(rs)){
            rs = RandomUtil.randomNumbers(8);
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> batChangeStatus(BatchChangeMbrStatusUpdRequest request) {
        for (Integer id : request.getIds()){
            mbrInfoMapper.updMbrStatus(id, request.getStatus(), request.getMerchantId());
        }
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> rechargeSubmit(RechargeSubmitRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getMbrId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        if (mb.getStatus()==MbrStatus.banned){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员已禁用");
        }
        MbrRechargeRecord rec = new MbrRechargeRecord();
        rec.setRecNo("r"+ IdUtil.getSnowflakeNextIdStr()).setMbrId(mb.getId()).setAmount(request.getAmount().abs())
                .setStatus(RecordStatus.paid).setMemo(request.getRemark());
        mbrRechargeRecordMapper.insert(rec);
        mbrAssetHelper.submitAssetChange(mb.getId(), request.getAmount(), AssetsFlwType.recharge_add, request.getRemark(), rec.getRecNo());
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> withdrawSubmit(WithdrawSubmitRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getMbrId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        if (mb.getStatus()==MbrStatus.banned){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员已禁用");
        }
        MbrWithdrawRecord rec = new MbrWithdrawRecord();
        rec.setRecNo("w"+ IdUtil.getSnowflakeNextIdStr()).setMbrId(mb.getId()).setAmount(request.getAmount().abs())
                .setStatus(request.getStatus()).setMemo(request.getRemark());
        mbrWithdrawRecordMapper.insert(rec);
        if (rec.getStatus()==RecordStatus.paid){
            mbrAssetHelper.submitAssetChange(mb.getId(),request.getAmount(), AssetsFlwType.withdraw_reduce, request.getRemark(), rec.getRecNo());
        }
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> manualBalance(ManualAmountRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getMbrId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        if (mb.getStatus()==MbrStatus.banned){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员已禁用");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO)==0){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "金额不能为0");
        }
        AssetsFlwType type = request.getAmount().compareTo(BigDecimal.ZERO)>0?AssetsFlwType.system_add:AssetsFlwType.system_reduce;
        mbrAssetHelper.submitAssetChange(mb.getId(), request.getAmount(), type, request.getRemark(), request.getOperator().id());
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> manualIntegral(ManualAmountRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getMbrId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        if (mb.getStatus()==MbrStatus.banned){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员已禁用");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO)==0){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "金额不能为0");
        }
        AssetsFlwType type = request.getAmount().compareTo(BigDecimal.ZERO)>0?AssetsFlwType.integral_add:AssetsFlwType.integral_reduce;
        mbrAssetHelper.submitAssetChange(mb.getId(), request.getAmount(), type, request.getRemark(), request.getOperator().id());
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> updCpMark(MbrCpMarkUpdRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        mb.setCpMark(request.getCpMark());
        mbrInfoMapper.updateById(mb);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<CustomerChatDetail> chat(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        MbrInfo mb = mbrInfoMapper.selectById(request.getId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        if (!ctx.account().equals(mb.getCustomerAccount())){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "无权限，不是你的客户");
        }
        CustomerChat cc = customerChatMapper.getOneByMbrIdAndCustomerId(request.getId(), ctx.id());
        if (cc==null){
            cc = new CustomerChat();
            cc.setMbrId(request.getId()).setCustomerId(ctx.id());
            customerChatMapper.insert(cc);
        }
        BaseUpdateRequest req = new BaseUpdateRequest();
        req.setId(cc.getId());
        return customerChatService.detail(req);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> updVipLevel(MbrVipLevelUpdRequest request) {
        MbrInfo mb = mbrInfoMapper.selectById(request.getId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "会员不存在");
        }
        mb.setVipLevel(request.getVipLevel());
        mbrInfoMapper.updateById(mb);
        return ResultBody.success();
    }
}
