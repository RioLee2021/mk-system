package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 20:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderRecordPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(value = "订单号")
    @QueryCon
    private String orderNo;

    @ApiModelProperty(value = "产品ID",notes = "Options.do下拉")
    @QueryCon
    private Integer productId;

}
