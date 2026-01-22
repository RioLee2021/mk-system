package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:28
 */
@Getter
@AllArgsConstructor
public enum MbrStatus implements IDbEnums {
    normal("正常"),banned("禁用")
    ;

    private final String chName;

    @JsonCreator
    public static MbrStatus valueOf(int value) {
        for (MbrStatus e : MbrStatus.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}