package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:49
 */
@Getter
@AllArgsConstructor
public enum OrderPdStatus implements IDbEnums {
    await_paid("待付款"),
    paid("已付款"),
    repurchase_request("申请回购"),
    repurchase_success("回购成功"),
    repurchase_fail("回购失败"),
    ;

    private final String chName;

    @JsonCreator
    public static OrderPdStatus valueOf(int value) {
        for (OrderPdStatus e : OrderPdStatus.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}