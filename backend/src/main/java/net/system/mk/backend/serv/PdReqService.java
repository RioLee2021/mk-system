package net.system.mk.backend.serv;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.biz.vo.DistributionPdRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrPdRequestPagerRequest;
import net.system.mk.commons.dao.MbrPdRequestMapper;
import net.system.mk.commons.dao.OrderPdRecordMapper;
import net.system.mk.commons.dao.OrderRecordMapper;
import net.system.mk.commons.dao.ProductInfoMapper;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrPdRequest;
import net.system.mk.commons.pojo.OrderPdRecord;
import net.system.mk.commons.pojo.OrderRecord;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:45
 */
@Service
public class PdReqService {

    @Resource
    private MbrPdRequestMapper mbrPdRequestMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private OrderRecordMapper orderRecordMapper;
    @Resource
    private OrderPdRecordMapper orderPdRecordMapper;

    public PagerResult<MbrPdRequest> list(MbrPdRequestPagerRequest request) {
        QueryWrapper<Object> ew = OtherUtils.createIdDescWrapper(request, "mpr");
        if (request.getDone()!=null){
            if (request.getDone()){
                ew.isNotNull("mpr.pd_no");
            }else {
                ew.isNull("mpr.pd_no");
            }
        }
        return PagerResult.of(mbrPdRequestMapper.getPageByEw(request.toPage(), ew));
    }

    public List<DictItem> productOps() {
        return productInfoMapper.productOps();
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<Void> distribution(DistributionPdRequest request) {
        MbrPdRequest req = mbrPdRequestMapper.selectById(request.getId());
        if (req==null){
            throw new GlobalException(BUSINESS_ERROR, "数据不存在");
        }
        if (req.getPdNo()!=null){
            throw new GlobalException(BUSINESS_ERROR, "该数据已分配");
        }
        ProductInfo product = productInfoMapper.selectById(request.getProductId());
        if (product==null){
            throw new GlobalException(BUSINESS_ERROR, "产品不存在");
        }
        String ono = "AB"+ IdUtil.getSnowflakeNextIdStr();
        String pno = "PD"+ IdUtil.getSnowflakeNextIdStr();
        OrderRecord order = new OrderRecord().setOrderNo(ono).setOwnerId(0).setProductId(request.getProductId());
        //机器人
        OrderPdRecord bot = new OrderPdRecord().setOrderNo(ono).setPdNo(ono).setMbrId(0).setOrderPrice(product.getOrderPrice()).setCommission(product.getCommission()).setPdStatus(OrderPdStatus.paid);
        OrderPdRecord pd = new OrderPdRecord().setOrderNo(ono).setPdNo(pno).setMbrId(req.getMbrId()).setOrderPrice(product.getOrderPrice()).setCommission(product.getCommission()).setPdStatus(OrderPdStatus.await_paid);
        orderRecordMapper.insert(order);
        orderPdRecordMapper.insert(bot);
        orderPdRecordMapper.insert(pd);
        req.setPdNo(pno);
        mbrPdRequestMapper.updateById(req);
        return ResultBody.success();
    }
}
