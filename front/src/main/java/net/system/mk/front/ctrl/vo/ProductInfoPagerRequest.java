package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 11:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductInfoPagerRequest extends PagerRequest {

    @ApiModelProperty("品牌ID")
    @QueryCon
    private Integer brandId;


}
