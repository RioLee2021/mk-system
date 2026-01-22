package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:30
 */
@Getter
@AllArgsConstructor
public enum MbrType implements IDbEnums {
    un_login("未登录"),
    un_bind_bank_card("未绑定银行卡"),
    active("已激活"),
    ;

    private final String chName;

    @JsonCreator
    public static MbrType valueOf(int value) {
        for (MbrType e : MbrType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}