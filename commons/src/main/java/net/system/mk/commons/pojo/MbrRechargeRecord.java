package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.RecordStatus;

/**
 * 用户充值记录
 */
@ApiModel(description="用户充值记录")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MbrRechargeRecord extends BasePO {
    /**
    * 充值记录编号
    */
    @ApiModelProperty(value="充值记录编号")
    private String recNo;

    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 充值金额
    */
    @ApiModelProperty(value="充值金额")
    private BigDecimal amount;

    /**
    * 记录状态(RecordStatus)
    */
    @ApiModelProperty(value="记录状态(RecordStatus)")
    private RecordStatus status;

    /**
    * 充值备注
    */
    @ApiModelProperty(value="充值备注")
    private String memo;
}