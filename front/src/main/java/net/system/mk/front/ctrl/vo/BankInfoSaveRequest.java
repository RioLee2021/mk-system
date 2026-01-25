package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 12:53
 */
@Data
public class BankInfoSaveRequest {

    @ApiModelProperty(value = "真实姓名",  required = true)
    @NotBlank(message = "actual name not be blank")
    private String actualName;

    @ApiModelProperty(value = "银行名称",notes = "下拉（前端写死）",  required = true)
    @NotBlank(message = "bank name not be blank")
    private String bankName;

    @ApiModelProperty(value = "银行卡号",  required = true)
    @NotBlank(message = "bank card no not be blank")
    private String bankCardNo;

    @ApiModelProperty(value = "提现密码",  required = true)
    @NotBlank(message = "withdraw password not be blank")
    private String withdrawPassword;
}
