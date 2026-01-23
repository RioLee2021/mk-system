package net.system.mk.backend.ctrl.operator.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrRechargeRecordPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(value = "订单号")
    @QueryCon
    private String recNo;

    @ApiModelProperty(value = "会员ID")
    @QueryCon
    private Integer mbrId;
}
