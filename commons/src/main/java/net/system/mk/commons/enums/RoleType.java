package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2025-11-2025/11/15/0015 13:19
 */
@Getter
@AllArgsConstructor
public enum RoleType implements IDbEnums {
    SuperAdmin("超级管理员"), WebMaster("站长"), Manager("管理员"), Worker("维护人员"),Member("会员"),
    ;

    private final String chName;

    @JsonCreator
    public static RoleType valueOf(int value) {
        for (RoleType e : RoleType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }

}