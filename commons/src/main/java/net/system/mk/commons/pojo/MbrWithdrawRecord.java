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
 * 用户提现记录
 */
@ApiModel(description="用户提现记录")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MbrWithdrawRecord extends BasePO {
    /**
    * 提现记录编号
    */
    @ApiModelProperty(value="提现记录编号")
    private String recNo;

    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 提现金额
    */
    @ApiModelProperty(value="提现金额")
    private BigDecimal amount;

    /**
    * 记录状态(RecordStatus)
    */
    @ApiModelProperty(value="记录状态(RecordStatus)")
    private RecordStatus status;

    /**
    * 提现备注
    */
    @ApiModelProperty(value="提现备注")
    private String memo;
}