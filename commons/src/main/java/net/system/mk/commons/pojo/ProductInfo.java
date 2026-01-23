package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 产品信息
 */
@ApiModel(description = "产品信息")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ProductInfo extends BasePO {
    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

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
     * 拼单价格
     */
    @ApiModelProperty(value = "拼单价格")
    private BigDecimal orderPrice;

    /**
     * 佣金金额
     */
    @ApiModelProperty(value = "佣金金额")
    private BigDecimal commission;

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