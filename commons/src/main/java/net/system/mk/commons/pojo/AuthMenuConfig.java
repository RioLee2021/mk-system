package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.RoleType;

/**
 * 权限菜单配置
 */
@ApiModel(description="权限菜单配置")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class AuthMenuConfig extends BasePO {
    /**
    * 角色类型(RoleType)
    */
    @ApiModelProperty(value="角色类型(RoleType)")
    private RoleType roleType;

    /**
    * 菜单ID
    */
    @ApiModelProperty(value="菜单ID")
    private Integer menuId;
}