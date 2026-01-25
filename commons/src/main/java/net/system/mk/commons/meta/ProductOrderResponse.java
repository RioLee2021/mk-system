package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.pojo.OrderPdRecord;

import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 12:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductOrderResponse extends OrderPdRecord {

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 图片1地址
     */
    @ApiModelProperty(value = "图片1地址")
    private String pic1Url;

    /**
     * 图片2地址
     */
    @ApiModelProperty(value = "图片2地址")
    private String pic2Url;

    /**
     * 图片3地址
     */
    @ApiModelProperty(value = "图片3地址")
    private String pic3Url;

    /**
     * 标签价格
     */
    @ApiModelProperty(value = "标签价格")
    private BigDecimal labelPrice;


    /**
     * 产品描述
     */
    @ApiModelProperty(value = "产品描述")
    private String productDesc;

    /**
     * 优惠标识
     */
    @ApiModelProperty(value = "优惠标识")
    private Boolean specialOffer;
}
