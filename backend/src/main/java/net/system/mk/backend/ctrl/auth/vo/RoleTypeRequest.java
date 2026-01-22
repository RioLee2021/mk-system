package net.system.mk.backend.ctrl.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.RoleType;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:16
 */
@Data
public class RoleTypeRequest {

    @ApiModelProperty(value = "角色类型",required = true)
    @NotNull(message = "角色类型不能为空")
    private RoleType roleType;
}
