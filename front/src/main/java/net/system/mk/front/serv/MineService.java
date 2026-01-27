package net.system.mk.front.serv;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.*;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.*;
import net.system.mk.commons.pojo.*;
import net.system.mk.commons.utils.OtherUtils;
import net.system.mk.front.ctrl.vo.AvatarUpdRequest;
import net.system.mk.front.ctrl.vo.BankInfoSaveRequest;
import net.system.mk.front.ctrl.vo.MsgContentRequest;
import net.system.mk.front.ctx.MemberContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static net.system.mk.commons.expr.GlobalErrorCode.LOGIN_INVALID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:56
 */
@Service
@Slf4j
public class MineService {

    @Resource
    private ICtxHelper iCtxHelper;

    @Resource
    private MbrRechargeRecordMapper mbrRechargeRecordMapper;
    @Resource
    private MbrWithdrawRecordMapper mbrWithdrawRecordMapper;
    @Resource
    private MbrAssetsFlwMapper mbrAssetsFlwMapper;
    @Resource
    private OrderPdRecordMapper orderPdRecordMapper;
    @Resource
    private MbrInfoMapper mbrInfoMapper;
    @Resource
    private ChatMsgLogMapper chatMsgLogMapper;
    @Resource
    private CustomerChatMapper customerChatMapper;

    public PagerResult<MbrRechargeRecord> rechargeRecord(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        QueryWrapper<MbrRechargeRecord> q = OtherUtils.createIdDescWrapper(request);
        q.eq("mbr_id", ctx.id());
        return PagerResult.of(mbrRechargeRecordMapper.selectPage(request.toPage(), q));
    }

    public PagerResult<MbrWithdrawRecord> withdrawRecord(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        QueryWrapper<MbrWithdrawRecord> q = OtherUtils.createIdDescWrapper(request);
        q.eq("mbr_id", ctx.id());
        return PagerResult.of(mbrWithdrawRecordMapper.selectPage(request.toPage(), q));
    }

    public PagerResult<MbrAssetsFlw> fundDetails(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        log.warn("当前CTX：{}",ctx);
        QueryWrapper<MbrAssetsFlw> q = OtherUtils.createIdDescWrapper(request);
        log.warn("当前Q：{},当前ID：{}",q,ctx.id());
        q.eq("mbr_id", ctx.id());
        return PagerResult.of(mbrAssetsFlwMapper.selectPage(request.toPage(), q));
    }

    public PagerResult<ProductOrderResponse> awaitPayOrder(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return PagerResult.of(orderPdRecordMapper.getProductOrderResponsePageByMbrIdAndPdStatus(request.toPage(), ctx.id(), OrderPdStatus.await_paid));
    }

    public PagerResult<ProductOrderResponse> completedOrder(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return PagerResult.of(orderPdRecordMapper.getProductOrderResponsePageByMbrIdAndPdStatus(request.toPage(), ctx.id(), OrderPdStatus.paid));
    }

    public PagerResult<ProductOrderResponse> repurchaseOrder(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return PagerResult.of(orderPdRecordMapper.getProductOrderResponsePageByMbrIdAndPdStatus(request.toPage(), ctx.id(), OrderPdStatus.repurchase_request));
    }

    public PagerResult<ProductOrderResponse> repurchasedOrder(PagerRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return PagerResult.of(orderPdRecordMapper.getProductOrderResponsePageByMbrIdAndPdStatusRange(request.toPage(), ctx.id(), OrderPdStatus.repurchase_success,  OrderPdStatus.repurchase_fail));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> repurchaseSubmit(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        OrderPdRecord opr = orderPdRecordMapper.selectById(request.getId());
        if (opr == null|| !Objects.equals(opr.getMbrId(), ctx.id())){
            throw new GlobalException(BUSINESS_ERROR, "The order does not exist");
        }
        if (!opr.getPdStatus().equals(OrderPdStatus.paid)){
            throw new GlobalException(BUSINESS_ERROR, "The order status is incorrect");
        }
        opr.setPdStatus(OrderPdStatus.repurchase_request);
        orderPdRecordMapper.updateById(opr);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> saveBankInfo(BankInfoSaveRequest request) {
        MemberContext ctx = (MemberContext) iCtxHelper.getWebCtx();
        if (ctx == null){
            throw new GlobalException(LOGIN_INVALID);
        }
        MbrInfo mb = mbrInfoMapper.selectById(ctx.id());
        if (StrUtil.isBlank(mb.getWithdrawPassword())){
            throw new GlobalException(BUSINESS_ERROR, "please set withdraw password first");
        }
        if (!mb.getWithdrawPassword().equals(request.getWithdrawPassword())){
            throw new GlobalException(BUSINESS_ERROR, "withdraw password error");
        }
        mb.setActualName(request.getActualName()).setBankName(request.getBankName()).setBankCardNo(request.getBankCardNo());
        mbrInfoMapper.updateById(mb);
        ctx.loadMbrInfo(mb);
        iCtxHelper.putWebCtx(ctx);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> modifyAvatar(AvatarUpdRequest request) {
        MemberContext ctx = (MemberContext) iCtxHelper.getWebCtx();
        if (ctx == null){
            throw new GlobalException(LOGIN_INVALID);
        }
        MbrInfo mb = mbrInfoMapper.selectById(ctx.id());
        mb.setLogoNumber(request.getAvatar());
        mbrInfoMapper.updateById(mb);
        ctx.loadMbrInfo(mb);
        iCtxHelper.putWebCtx(ctx);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> modifyLoginPassword(PasswordUpdRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        if (ctx == null){
            throw new GlobalException(LOGIN_INVALID);
        }
        MbrInfo mb = mbrInfoMapper.selectById(ctx.id());
        if (!mb.getLoginPassword().equals(request.getOldPassword())){
            throw new GlobalException(BUSINESS_ERROR, "old password error");
        }
        mb.setLoginPassword(request.getNewPassword());
        mbrInfoMapper.updateById(mb);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> modifyWithdrawPassword(PasswordUpdRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        if (ctx == null){
            throw new GlobalException(LOGIN_INVALID);
        }
        MbrInfo mb = mbrInfoMapper.selectById(ctx.id());
        if (StrUtil.isNotBlank(mb.getWithdrawPassword())&&  !mb.getWithdrawPassword().equals(request.getOldPassword())){
            throw new GlobalException(BUSINESS_ERROR, "old password error");
        }
        mb.setWithdrawPassword(request.getNewPassword());
        mbrInfoMapper.updateById(mb);
        return ResultBody.success();
    }

    public List<ChatMsgLog> getMessage(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return chatMsgLogMapper.getLatestMsgByMbrIdAndLastId(ctx.id(), request.getId());
    }


    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> sendMessage(MsgContentRequest request) {
        IBaseContext  ctx = iCtxHelper.getWebCtx();
        CustomerChat cc = customerChatMapper.selectById(request.getChatId());
        if (cc == null|| !Objects.equals(cc.getMbrId(), ctx.id())){
            throw new GlobalException(BUSINESS_ERROR, "The chat does not exist");
        }
        ChatMsgLog cml = new ChatMsgLog();
        String content = request.getContent();
        if (content.startsWith("img[")&&content.endsWith("]")){
            content = content.replaceFirst("^img\\[(.*)\\]$", "$1");
            cml.setImageFlg(Boolean.TRUE);
        }
        cml.setContent(content).setChatId(request.getChatId()).setCustomerFlg(Boolean.FALSE).setOwnerId(ctx.id());
        chatMsgLogMapper.insert(cml);
        customerChatMapper.submitReply(request.getChatId(), Boolean.FALSE);
        return ResultBody.success();
    }
}
