package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.RecordStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:15
 */
@Data
public class WithdrawSubmitRequest {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(value = "会员ID",required = true)
    @NotNull(message = "会员ID不能为空")
    private Integer mbrId;

    @ApiModelProperty(value = "充值金额",required = true)
    @NotNull(message = "充值金额不能为空")
    private BigDecimal amount;

    @ApiModelProperty(value = "充值备注")
    @NotBlank(message = "充值备注不能为空")
    private String remark;

    @ApiModelProperty(value = "充值状态",required = true)
    @NotNull(message = "充值状态不能为空")
    private RecordStatus status;
}
