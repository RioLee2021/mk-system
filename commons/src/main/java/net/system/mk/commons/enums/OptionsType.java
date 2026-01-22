package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.system.mk.commons.interfaces.IDictItemAbel;

/**
 * @author USER
 * @date 2025-04-2025/4/16/0016 9:24
 */
@Getter
@AllArgsConstructor
public enum OptionsType implements IDbEnums {

    ;

    private final String chName;
    private final Class<? extends IDictItemAbel> dictItemClass;
    private final boolean merchantInject;

    @JsonCreator
    public static OptionsType valueOf(int value) {
        for (OptionsType e : OptionsType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}