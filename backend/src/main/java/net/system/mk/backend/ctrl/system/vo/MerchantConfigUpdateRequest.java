package net.system.mk.backend.ctrl.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.OtpUpdateRequest;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 11:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MerchantConfigUpdateRequest extends OtpUpdateRequest {

    /**
     * 商户名称
     */
    @ApiModelProperty(value = "商户名称", required = true)
    @NotBlank(message = "商户名称不能为空")
    private String mchName;


    /**
     * 商户api白名单
     */
    @ApiModelProperty(value = "商户api白名单", required = true)
    @NotBlank(message = "商户api白名单不能为空")
    private String apiWhiteList;


    /**
     * 商户备注
     */
    @ApiModelProperty(value = "商户备注", required = true)
    @NotBlank(message = "商户备注不能为空")
    private String memo;
}
