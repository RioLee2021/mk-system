package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 12:52
 */
@Data
public class PasswordUpdRequest {

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @ApiModelProperty(required = true,value = "新密码")
    @NotBlank(message = "new password not be blank")
    private String newPassword;
}
