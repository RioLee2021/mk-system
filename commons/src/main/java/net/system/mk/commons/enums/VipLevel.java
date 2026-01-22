package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:29
 */
@Getter
@AllArgsConstructor
public enum VipLevel implements IDbEnums {
    member( "普通会员",1.0),
    vip1( "VIP1",1.01),
    vip2( "VIP2",1.02),
    vip3( "VIP3",1.03),
    vip4( "VIP4",1.04),
    vip5( "VIP5",1.05),
    vip6( "VIP6",1.06),
    vip7( "VIP7",1.07),
    vip8( "VIP8",1.08),
    ;

    private final String chName;
    private final Double commissionRate;

    @JsonCreator
    public static VipLevel valueOf(int value) {
        for (VipLevel e : VipLevel.values()) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }
}