package net.system.mk.backend.ctrl.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MerchantConfigPagerRequest extends PagerRequest {

    @ApiModelProperty("商户编号")
    @QueryCon
    private String mchCode;

    @ApiModelProperty("商户名称")
    @QueryCon
    private String mchName;

    @ApiModelProperty("币种")
    @QueryCon
    private String currency;
}
