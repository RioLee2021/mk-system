package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:48
 */
@Getter
@AllArgsConstructor
public enum OrderStatus implements IDbEnums {
    running("运行中"),
    finished("已完成"),
    ;

    private final String chName;

    @JsonCreator
    public static OrderStatus valueOf(int value) {
        for (OrderStatus e : OrderStatus.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}