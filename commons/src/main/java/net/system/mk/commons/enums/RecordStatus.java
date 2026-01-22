package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 13:16
 */
@Getter
@AllArgsConstructor
public enum RecordStatus implements IDbEnums {
    paying("支付中"),
    paid("支付成功"),
    failed("支付失败"),
    ;

    private final String chName;

    @JsonCreator
    public static RecordStatus valueOf(int value) {
        for (RecordStatus e : RecordStatus.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}