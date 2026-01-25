package net.system.mk.front.serv;

import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.MbrPdRequestMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrPdRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:55
 */
@Service
public class ActivityService {

    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private MbrPdRequestMapper mbrPdRequestMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<Void> activityRequest() {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        if (mbrPdRequestMapper.cntRequestingByMbrId(ctx.id())>0){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "you have already received the event");
        }
        mbrPdRequestMapper.insert(new MbrPdRequest().setMbrId(ctx.id()));
        return ResultBody.success();
    }
}
