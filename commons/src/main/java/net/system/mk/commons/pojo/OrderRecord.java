package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单列表
 */
@ApiModel(description = "订单列表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OrderRecord extends BasePO {
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * 发起用户ID
     */
    @ApiModelProperty(value = "发起用户ID")
    private Integer ownerId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private Integer productId;


}