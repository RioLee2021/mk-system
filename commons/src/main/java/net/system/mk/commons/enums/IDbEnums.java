package net.system.mk.commons.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 枚举接口
 * @author USER
 */
public interface IDbEnums extends IEnum<Integer> {

    /**
     * 获取中文名称
     * @return str
     */
    String getChName();

    /**
     * 下标
     * @return int
     */
    int ordinal();

    /**
     * 枚举名
     * @return str
     */
    String name();

    /**
     * 枚举值
     * @return int
     */
    @Override
    @JsonValue
    default Integer getValue() {
        return this.ordinal();
    }
}
