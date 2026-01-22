package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2025-11-2025/11/26/0026 11:02
 */
@Getter
@AllArgsConstructor
public enum MenuScope implements IDbEnums {
    both( "所有"),platform("平台"),merchant("商户"),
    ;

    private final String chName;

    @JsonCreator
    public static MenuScope valueOf(int value) {
        for (MenuScope e : MenuScope.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}