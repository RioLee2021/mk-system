package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2025-11-2025/11/23/0023 8:04
 */
@Getter
@AllArgsConstructor
public enum CtxScope implements IDbEnums {
    backend("后端"),
    wap("前端"),
    ;

    private final String chName;

    @JsonCreator
    public static CtxScope valueOf(int value) {
        for (CtxScope e : CtxScope.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}