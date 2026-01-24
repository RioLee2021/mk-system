package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 10:59
 */
@Data
public class MbrLoginRequest {

    @ApiModelProperty(value = "手机号")
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotBlank
    private String password;
}
