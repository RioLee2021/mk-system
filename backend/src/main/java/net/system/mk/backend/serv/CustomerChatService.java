package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.operator.vo.CustomerChatResponsePagerRequest;
import net.system.mk.backend.ctrl.operator.vo.SendChatRequest;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.CustomerChatMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ext.CustomerChatDetail;
import net.system.mk.commons.ext.CustomerChatResponse;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.ChatMsgLog;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    public PagerResult<CustomerChatResponse> list(CustomerChatResponsePagerRequest request) {
        IBaseContext ctx = iCtxHelper.getBackendCtx();
        request.setCustomerId(ctx.id());
        return PagerResult.of(customerChatMapper.getPageByEw(request.toPage(), OtherUtils.createIdDescWrapper(request,"cc")));
    }

    public ResultBody<CustomerChatDetail> detail(BaseUpdateRequest request) {
        throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "待完成");
        //todo 待完成
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> deleteChatLog(BaseUpdateRequest request) {
        throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "待完成");
        //todo 待完成
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> sendChat(SendChatRequest request) {
        throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "待完成");
        //todo 待完成
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<ChatMsgLog> latestMsg(BaseUpdateRequest request) {
        throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "待完成");
        //todo 待完成
    }
}
