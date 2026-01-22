package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.basic.vo.OrderRecordPagerRequest;
import net.system.mk.commons.dao.OrderRecordMapper;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.OrderRecord;
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

    public PagerResult<OrderRecord> list(OrderRecordPagerRequest request) {
        return PagerResult.of(orderRecordMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }
}
