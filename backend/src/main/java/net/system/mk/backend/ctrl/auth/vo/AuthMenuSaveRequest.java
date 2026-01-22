package net.system.mk.backend.ctrl.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.meta.PermLoggedRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthMenuSaveRequest extends PermLoggedRequest {

    @ApiModelProperty(value = "角色类型",required = true)
    @NotNull(message = "角色类型不能为空")
    private RoleType roleType;

    @ApiModelProperty(value = "菜单ID",required = true)
    @NotNull(message = "菜单ID不能为空")
    private List<Integer> menuIds;
}
