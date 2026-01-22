package net.system.mk.backend.ctrl.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.meta.PermLoggedRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermUserAddRequest extends PermLoggedRequest {

    @ApiModelProperty(value = "商户ID",notes = "Options下拉,0为总平台",required = true)
    @NotNull(message = "商户ID不能为空")
    private Integer merchantId;

    @ApiModelProperty(required = true,  value = "账号")
    @NotBlank(message = "账号不能为空")
    private String account;

    @ApiModelProperty(required = true,  value = "密码")
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-fA-F0-9]{32}$",message = "密码格式错误,密码为md5")
    private String password;

    @ApiModelProperty(required = true,  value = "角色")
    @NotNull(message = "角色不能为空")
    private RoleType roleType;
}
