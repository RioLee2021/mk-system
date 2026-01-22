package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2025-11-2025/11/26/0026 10:57
 */
@Getter
@AllArgsConstructor
public enum PermMenuGroup implements IDbEnums {
    system(1,  "系统模块", "/system", "el-icon-setting"),
    auth(2, "权限模块", "/auth", "el-icon-s-custom"),
    log(3, "日志模块", "/log", "el-icon-s-tools"),
    basic_setting(4, "基础设置", "/basic-setting", "el-icon-s-tools"),
    biz_manage(5, "业务管理", "/biz-manage", "el-icon-s-tools"),
    operator_manage(6, "数据管理", "/operator-manage", "el-icon-s-tools"),
    ;

    private final int sortNo;
    private final String chName;
    private final String path;
    private final String icon;

    @JsonCreator
    public static PermMenuGroup valueOf(int value) {
        for (PermMenuGroup e : PermMenuGroup.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}