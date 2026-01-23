package net.system.mk.backend.serv;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.biz.vo.OrderPdRecordPagerRequest;
import net.system.mk.commons.dao.OrderPdRecordMapper;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.OrderPdRecord;
import net.system.mk.commons.serv.MbrAssetHelper;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:27
 */
@Service
public class OrderPdRecordService {

    @Resource
    private OrderPdRecordMapper orderPdRecordMapper;
    @Resource
    private MbrAssetHelper mbrAssetHelper;

    public PagerResult<OrderPdRecord> list(OrderPdRecordPagerRequest request) {
        QueryWrapper<Object> ew = OtherUtils.createIdDescWrapper(request, "opr");
        if (StrUtil.isNotBlank(request.getMember())) {
            ew.and(q -> q.eq("opr.pd_no", request.getMember()).or().eq("mi.phone", request.getMember()));
        }
        return PagerResult.of(orderPdRecordMapper.getPageByEw(request.toPage(), ew));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> confirm(BaseUpdateRequest request) {
        OrderPdRecord pd = orderPdRecordMapper.selectById(request.getId());
        if (pd == null){
            throw new GlobalException(BUSINESS_ERROR, "数据不存在");
        }
        if (pd.getPdStatus().ordinal()>OrderPdStatus.repurchase_request.ordinal()){
            throw new GlobalException(BUSINESS_ERROR, "数据已处理");
        }
        pd.setPdStatus(OrderPdStatus.repurchase_success);
        orderPdRecordMapper.updateById(pd);
        mbrAssetHelper.submitAssetChange(pd.getMbrId(),pd.getOrderPrice(), AssetsFlwType.repurchase_add,null,pd.getPdNo());
        mbrAssetHelper.submitAssetChange(pd.getMbrId(),pd.getCommission(), AssetsFlwType.repurchase_commission_add,null,pd.getPdNo());
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> reject(BaseUpdateRequest request) {
        OrderPdRecord pd = orderPdRecordMapper.selectById(request.getId());
        if (pd == null){
            throw new GlobalException(BUSINESS_ERROR, "数据不存在");
        }
        if (pd.getPdStatus().ordinal()>OrderPdStatus.repurchase_request.ordinal()){
            throw new GlobalException(BUSINESS_ERROR, "数据已处理");
        }
        pd.setPdStatus(OrderPdStatus.repurchase_fail);
        orderPdRecordMapper.updateById(pd);
        return ResultBody.success();
    }
}
