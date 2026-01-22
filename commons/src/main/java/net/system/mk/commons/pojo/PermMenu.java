package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;

/**
 * 菜单
 */
@ApiModel(description="菜单")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class PermMenu extends BasePO {
    /**
    * 菜单组(PermMenuGroup)
    */
    @ApiModelProperty(value="菜单组(PermMenuGroup)")
    private PermMenuGroup permMenuGroup;

    /**
    * 菜单名称
    */
    @ApiModelProperty(value="菜单名称")
    private String menuName;

    /**
    * 菜单URL路径
    */
    @ApiModelProperty(value="菜单URL路径")
    private String menuPath;

    /**
    * 菜单图标
    */
    @ApiModelProperty(value="菜单图标")
    private String icon;

    /**
    * 排序号
    */
    @ApiModelProperty(value="排序号")
    private Integer sortNo;

    /**
    * 菜单作用域(MenuScope)
    */
    @ApiModelProperty(value="菜单作用域(MenuScope)")
    private MenuScope menuScope;

    /**
    * 菜单是否为URI
    */
    @ApiModelProperty(value="菜单是否为URI")
    private Boolean uriFlag;
}