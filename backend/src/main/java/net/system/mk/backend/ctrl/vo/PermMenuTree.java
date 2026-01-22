package net.system.mk.backend.ctrl.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.system.mk.commons.pojo.PermMenu;

import java.util.List;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 10:26
 */
@Data
@Accessors(chain = true)
public class PermMenuTree {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单路径")
    private String menuPath;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "子菜单")
    private List<PermMenuTree> children= Lists.newArrayList();

    public static PermMenuTree of(PermMenu menu){
        return new PermMenuTree()
                .setId(menu.getId())
                .setMenuName(menu.getMenuName())
                .setMenuPath(menu.getMenuPath())
                .setIcon(menu.getIcon())
                .setSelected(false)
                .setSortNo(menu.getSortNo());
    }
}
