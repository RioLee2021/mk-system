package net.system.mk.commons.conf;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.interfaces.ILoggerPrintAble;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author USER
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig implements ILoggerPrintAble {
    @ApiModelProperty(value = "系统版本")
    private String systemVersion;
    @ApiModelProperty(value = "系统名称")
    private String name;
    @ApiModelProperty(value = "IP数据库文件路径")
    private String ipXdbPath;
    @ApiModelProperty(value = "时区")
    private Integer zoneOffset;
    @ApiModelProperty(value = "每秒处理请求")
    private Integer qps;
    @ApiModelProperty(value = "国际化开关")
    private Boolean i18n;
    @ApiModelProperty(value = "测试模式")
    private Boolean testMode;
    @ApiModelProperty(value = "TG机器人TOKEN")
    private String tgBotToken;

    @Override
    public String toLoggerPrintString() {
        return OtherUtils.bean2print("当前配置", this);
    }
}
