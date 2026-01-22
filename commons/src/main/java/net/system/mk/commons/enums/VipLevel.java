package net.system.mk.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:29
 */
@Getter
@AllArgsConstructor
public enum VipLevel implements IDbEnums {
    member( "普通会员"),
    vip1( "VIP1"),
    vip2( "VIP2"),
    vip3( "VIP3"),
    vip4( "VIP4"),
    vip5( "VIP5"),
    vip6( "VIP6"),
    vip7( "VIP7"),
    vip8( "VIP8"),
    ;

    private final String chName;

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