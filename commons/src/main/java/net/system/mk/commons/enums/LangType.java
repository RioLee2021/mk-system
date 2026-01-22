package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:45
 */
@Getter
@AllArgsConstructor
public enum LangType implements IDbEnums {
    th_Th("ภาษาไทย"),en_US("English"),zh_CN("简体中文"),vi_VN("Tiếng Việt")
    ;

    private final String chName;

    @JsonCreator
    public static LangType valueOf(int value) {
        for (LangType e : LangType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}