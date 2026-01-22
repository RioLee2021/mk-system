package net.system.mk.commons.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.OrderStatus;

/**
 * 订单列表
 */
@ApiModel(description="订单列表")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class OrderRecord extends BasePO {
    /**
    * 订单编号
    */
    @ApiModelProperty(value="订单编号")
    private String orderNo;

    /**
    * 发起用户ID
    */
    @ApiModelProperty(value="发起用户ID")
    private Integer ownerId;

    /**
    * 产品ID
    */
    @ApiModelProperty(value="产品ID")
    private Integer productId;

    /**
    * 订单描述
    */
    @ApiModelProperty(value="订单描述")
    private String orderDesc;

    /**
    * 基础佣金
    */
    @ApiModelProperty(value="基础佣金")
    private BigDecimal baseCommission;

    /**
    * 拼单价格
    */
    @ApiModelProperty(value="拼单价格")
    private BigDecimal orderPrice;

    /**
     * 订单状态(OrderStatus)
     */
    @ApiModelProperty(value="订单状态(OrderStatus)")
    private OrderStatus orderStatus;

    /**
    * 优惠标识
    */
    @ApiModelProperty(value="优惠标识")
    private Boolean specialOffer;

    //表外字段

    @ApiModelProperty(value="回购人数")
    @TableField(exist = false)
    private Integer repurchaseCnt;
}