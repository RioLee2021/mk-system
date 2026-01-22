package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.PagerRequest;

import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 19:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductInfoPagerRequest extends PagerRequest {

    @ApiModelProperty(value = "产品名称")
    @QueryCon
    private String productName;

    @ApiModelProperty(value = "标签价格")
    @QueryCon
    private BigDecimal labelPrice;

    @ApiModelProperty(value = "品牌ID")
    @QueryCon
    private Integer brandId;

}
