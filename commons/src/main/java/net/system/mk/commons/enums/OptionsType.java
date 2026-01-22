package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.system.mk.commons.interfaces.IDictItemAbel;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.pojo.MerchantConfig;

/**
 * @author USER
 * @date 2025-04-2025/4/16/0016 9:24
 */
@Getter
@AllArgsConstructor
public enum OptionsType implements IDbEnums {
    merchant_config("商户下拉", MerchantConfig.class, true),
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