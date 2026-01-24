package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 10:26
 */
@Data
public class MbeRegisterRequest {

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "昵称",required = true)
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "登录密码",required = true)
    @NotBlank
    private String loginPassword;

    @ApiModelProperty(value = "邀请码",required = true)
    @NotBlank
    private String inviteCode;
}
