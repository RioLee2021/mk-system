package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.BaseUpdateRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/28/0028 14:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductInfoUpdRequest extends BaseUpdateRequest {


    /**
     * 产品名称
     */
    @ApiModelProperty(value="产品名称",required = true)
    @NotBlank(message = "产品名称不能为空")
    private String productName;


    /**
     * 标签价格
     */
    @ApiModelProperty(value="标签价格",required = true)
    @NotNull(message = "标签价格不能为空")
    private BigDecimal labelPrice;

    /**
     * 拼单价格
     */
    @ApiModelProperty(value="拼单价格",required = true)
    @NotNull(message = "拼单价格不能为空")
    private BigDecimal orderPrice;

    @ApiModelProperty(value="佣金",required = true)
    @NotNull(message = "佣金不能为空")
    private BigDecimal commission;

    @ApiModelProperty(value="产品描述")
    private String productDesc;

    @ApiModelProperty(value="是否特价",required = true)
    @NotNull(message = "是否特价不能为空")
    private Boolean specialOffer;
}
