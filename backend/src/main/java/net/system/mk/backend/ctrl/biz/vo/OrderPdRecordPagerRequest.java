package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPdRecordPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(value = "订单号/手机号")
    private String member;

    @ApiModelProperty(value = "订单状态")
    @QueryCon
    private OrderPdStatus pdStatus;
}
