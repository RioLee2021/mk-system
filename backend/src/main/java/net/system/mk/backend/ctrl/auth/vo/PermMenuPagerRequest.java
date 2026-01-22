package net.system.mk.backend.ctrl.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerRequest;
import net.system.mk.commons.meta.QOP;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 21:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermMenuPagerRequest extends PagerRequest {

    @ApiModelProperty(value = "菜单组",notes = "枚举字典（PermMenuGroup）")
    @QueryCon
    private PermMenuGroup permMenuGroup;

    @ApiModelProperty(value = "菜单名称")
    @QueryCon
    private String menuName;

    @ApiModelProperty(value = "菜单路径")
    @QueryCon(op= QOP.likeRight)
    private String menuPath;

    @ApiModelProperty(value = "菜单作用域",notes = "枚举字典（MenuScope）")
    @QueryCon
    private MenuScope menuScope;

    @ApiModelProperty(value = "是否是URI")
    @QueryCon
    private Boolean uriFlag;
}
