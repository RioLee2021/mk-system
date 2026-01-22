package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.OrderPdStatus;

/**
 * 拼单参与记录
 */
@ApiModel(description="拼单参与记录")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class OrderPdRecord extends BasePO {
    /**
    * 订单编号
    */
    @ApiModelProperty(value="订单编号")
    private String orderNo;

    /**
    * 拼单编号
    */
    @ApiModelProperty(value="拼单编号")
    private String pdNo;

    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 拼单价格
    */
    @ApiModelProperty(value="拼单价格")
    private BigDecimal orderPrice;

    /**
    * 佣金
    */
    @ApiModelProperty(value="佣金")
    private BigDecimal commission;

    /**
     * 拼单状态(OrderPdStatus)
     */
    @ApiModelProperty(value="拼单状态(OrderPdStatus)")
    private OrderPdStatus pdStatus;
}