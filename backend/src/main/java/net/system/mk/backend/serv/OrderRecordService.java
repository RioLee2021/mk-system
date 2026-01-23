package net.system.mk.backend.serv;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.basic.vo.OrderRecordDetailRequest;
import net.system.mk.backend.ctrl.basic.vo.OrderRecordPagerRequest;
import net.system.mk.commons.dao.OrderRecordMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ext.OrderRecordDetailResponse;
import net.system.mk.commons.ext.OrderRecordResponse;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 20:19
 */
@Service
public class OrderRecordService {

    @Resource
    private OrderRecordMapper orderRecordMapper;

    public PagerResult<OrderRecordResponse> list(OrderRecordPagerRequest request) {
        QueryWrapper<Object> q = OtherUtils.createIdDescWrapper(request, "ord");
        q.groupBy("ord.id");
        return PagerResult.of(orderRecordMapper.getPageByEw(request.toPage(), q));
    }

    public ResultBody<OrderRecordDetailResponse> detail(OrderRecordDetailRequest request) {
        throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "待完成");
        //todo 待完成
    }
}
