package net.system.mk.commons.conf;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import net.system.mk.commons.enums.OptionsType;

/**
 * @author USER
 * @date 2025-04-2025/4/16/0016 10:40
 */
public class OptionsTableNameHandler implements TableNameHandler {

    private static final ThreadLocal<OptionsType> TABLE_NAME = new ThreadLocal<>();

    public static void setTableName(OptionsType optionsType) {
        TABLE_NAME.set(optionsType);
    }

    public static void remove() {
        TABLE_NAME.remove();
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if ("dynamic_tb".equalsIgnoreCase(tableName)) {
            OptionsType type = TABLE_NAME.get();
            if (type != null) {
                return type.name();
            }
        }
        return tableName;
    }
}
