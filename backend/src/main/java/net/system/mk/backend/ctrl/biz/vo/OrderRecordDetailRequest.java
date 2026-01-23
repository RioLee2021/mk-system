package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 11:49
 */
@Data
public class OrderRecordDetailRequest {

    @ApiModelProperty(value = "订单号",required = true)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;
}
