package net.system.mk.backend.ctrl.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthMenuConfigPagerRequest extends PagerRequest {

    @QueryCon
    @ApiModelProperty(value = "角色类型")
    private RoleType roleType;

    @QueryCon(name = "pm.menu_name")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @QueryCon(name = "pm.perm_menu_group")
    @ApiModelProperty(value = "菜单分组")
    private PermMenuGroup permMenuGroup;
}
