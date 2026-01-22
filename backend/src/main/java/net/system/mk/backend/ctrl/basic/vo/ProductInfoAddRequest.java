package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 19:43
 */
@Data
public class ProductInfoAddRequest {

    /**
     * 品牌ID
     */
    @ApiModelProperty(value="品牌ID",required = true)
    @NotNull(message = "品牌ID不能为空")
    private Integer brandId;

    /**
     * 产品名称
     */
    @ApiModelProperty(value="产品名称",required = true)
    @NotBlank(message = "产品名称不能为空")
    private String productName;

    /**
     * 图片1地址
     */
    @ApiModelProperty(value="图片1地址",required = true)
    @NotBlank(message = "图片1地址不能为空")
    private String pic1Url;

    /**
     * 图片2地址
     */
    @ApiModelProperty(value="图片2地址")
    private String pic2Url;

    /**
     * 图片3地址
     */
    @ApiModelProperty(value="图片3地址")
    private String pic3Url;

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
}
