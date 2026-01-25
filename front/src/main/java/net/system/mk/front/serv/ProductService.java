package net.system.mk.front.serv;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.OrderPdRecordMapper;
import net.system.mk.commons.dao.ProductInfoMapper;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.OrderPdRecord;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.commons.serv.MbrAssetHelper;
import net.system.mk.commons.utils.OtherUtils;
import net.system.mk.front.ctrl.vo.ProductInfoPagerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:54
 */
@Service
public class ProductService {

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private OrderPdRecordMapper orderPdRecordMapper;
    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private MbrAssetHelper mbrAssetHelper;

    public PagerResult<ProductInfo> list(ProductInfoPagerRequest request) {
        QueryWrapper<ProductInfo> q = OtherUtils.createOrderByWrapper(request,"order_price",false);
        return PagerResult.of(productInfoMapper.selectPage(request.toPage(),q));
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<Void> buy(BaseUpdateRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        ProductInfo pd = productInfoMapper.selectById(request.getId());
        if (pd == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "The product does not exist");
        }
        OrderPdRecord opr = orderPdRecordMapper.getPdRecordByMbrIdAndProductId(ctx.id(), request.getId());
        if (opr == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "Please match the order in the system");
        }
        if (!opr.getPdStatus().equals(OrderPdStatus.await_paid)){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "The order has been ("+opr.getPdStatus().name()+")");
        }
        mbrAssetHelper.checkBalance(ctx.id(),opr.getOrderPrice());
        mbrAssetHelper.submitAssetChange(ctx.id(),opr.getOrderPrice(), AssetsFlwType.order_payment_reduce, "拼单购买", opr.getOrderNo());
        opr.setPdStatus(OrderPdStatus.paid);
        orderPdRecordMapper.updateById(opr);
        return ResultBody.success();
    }
}
