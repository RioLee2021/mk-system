package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:51
 */
@Getter
@AllArgsConstructor
public enum AssetsFlwType implements IDbEnums {
    system_add("系统变更增加"),
    system_reduce("系统变更减少"),
    recharge_add("充值"),
    withdraw_reduce("提现"),
    commission_add("佣金"),
    order_payment_reduce("拼单付款"),
    repurchase_commission_add("回购佣金"),
    repurchase_add("回购"),
    withdraw_rollback_add("提现回滚"),
    integral_add("积分增加"),
    integral_reduce("积分减少"),
    ;

    private final String chName;

    @JsonCreator
    public static AssetsFlwType valueOf(int value) {
        for (AssetsFlwType e : AssetsFlwType.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}