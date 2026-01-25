package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.operator.vo.CustomerChatResponsePagerRequest;
import net.system.mk.backend.ctrl.operator.vo.SendChatRequest;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.*;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ext.CustomerChatDetail;
import net.system.mk.commons.ext.CustomerChatResponse;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.*;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 22:35
 */
@Service
public class CustomerChatService {

    @Resource
    private CustomerChatMapper customerChatMapper;
    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private MbrInfoMapper mbrInfoMapper;
    @Resource
    private MbrAssetsMapper mbrAssetsMapper;
    @Resource
    private MbrAssetsFlwMapper mbrAssetsFlwMapper;
    @Resource
    private AppConfig appConfig;
    @Resource
    private ChatMsgLogMapper chatMsgLogMapper;

    public PagerResult<CustomerChatResponse> list(CustomerChatResponsePagerRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        request.setCustomerId(ctx.id());
        return PagerResult.of(customerChatMapper.getPageByEw(request.toPage(), OtherUtils.createIdDescWrapper(request,"cc")));
    }

    public ResultBody<CustomerChatDetail> detail(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        CustomerChat cc = customerChatMapper.selectById(request.getId());
        if (cc==null||!cc.getCustomerId().equals(ctx.id())){
            throw new GlobalException(BUSINESS_ERROR, "没有该会员的聊天权限");
        }
        MbrInfo mb = mbrInfoMapper.selectById(cc.getMbrId());
        if (mb==null){
            throw new GlobalException(BUSINESS_ERROR, "会员不存在");
        }
        MbrAssets ma = mbrAssetsMapper.getByMbrId(cc.getMbrId());
        if (ma==null){
            throw new GlobalException(BUSINESS_ERROR, "会员资产不存在");
        }
        CustomerChatDetail rs = new CustomerChatDetail();
        rs.setChatId(cc.getId()).setMbrId(cc.getMbrId())
                .setPhone(mb.getPhone()).setRegisterRegion(mb.getRegisterRegion()).setRegisterIp(mb.getRegisterIp())
                .setLastLoginIp(mb.getLoginIp()).setLoginRegion(mb.getLoginRegion()).setLastLoginAt(mb.getLastLoginAt()).setRelationshipRoute(mb.getRelationshipRoute());
        rs.setCrateAt(mb.getCreateAt());
        rs.setBalance(ma.getBalance());
        //拿充提数据
        ZoneId zid = ZoneId.of(appConfig.getZoneId());
        LocalDateTime today = LocalDateTime.now(zid).toLocalDate().atStartOfDay();
        List<MbrAssetsFlw> flws = mbrAssetsFlwMapper.getListByMbrId(cc.getMbrId());
        BigDecimal tr = BigDecimal.ZERO;
        BigDecimal dr = BigDecimal.ZERO;
        BigDecimal tw = BigDecimal.ZERO;
        BigDecimal dw = BigDecimal.ZERO;
        for  (MbrAssetsFlw flw : flws) {
            switch (flw.getType()){
                case recharge_add:
                    tr = tr.add(flw.getAmount());
                    if (flw.getCreateAt().compareTo(today)>=0){
                        dr = dr.add(flw.getAmount());
                    }
                    break;
                case withdraw_reduce:
                    tw = tw.add(flw.getAmount());
                    if (flw.getCreateAt().compareTo(today)>=0){
                        dw = dw.add(flw.getAmount());
                    }
                    break;
            }
        }
        rs.setTotalRecharge(tr).setDailyRecharge(dr).setTotalWithdraw(tw).setDailyWithdraw(dw);
        rs.setChatMsgLogs(chatMsgLogMapper.getLatest100MsgByChatId(cc.getId()));
        return ResultBody.okData(rs);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> deleteChatLog(BaseUpdateRequest request) {
        ChatMsgLog chatMsgLog = chatMsgLogMapper.selectById(request.getId());
        if (chatMsgLog !=null){
            chatMsgLogMapper.deleteById(chatMsgLog);
        }
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> sendChat(SendChatRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        CustomerChat cc = customerChatMapper.selectById(request.getChatId());
        if (cc==null||!cc.getCustomerId().equals(ctx.id())){
            throw new GlobalException(BUSINESS_ERROR, "没有该会员的聊天权限");
        }
        cc.setReplyFlg(true);
        customerChatMapper.updateById(cc);
        ChatMsgLog cml = new ChatMsgLog();
        String content = request.getContent();
        cml.setCustomerFlg(Boolean.TRUE).setChatId(request.getChatId()).setOwnerId(ctx.id());
        if (content.startsWith("img[")&&content.endsWith("]")){
            cml.setImageFlg(Boolean.TRUE);
            content = content.substring(4,content.length()-2);
        }
        cml.setContent(content);
        chatMsgLogMapper.insert(cml);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public List<ChatMsgLog> latestMsg(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        CustomerChat cc = customerChatMapper.selectById(request.getId());
        if (cc==null||!cc.getCustomerId().equals(ctx.id())){
            throw new GlobalException(BUSINESS_ERROR, "没有该会员的聊天权限");
        }
        return chatMsgLogMapper.getLatest5MsgByChatId(cc.getId());
    }
}
