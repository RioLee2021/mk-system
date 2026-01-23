package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 聊天日志
 */
@ApiModel(description="聊天日志")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class ChatMsgLog extends BasePO {
    /**
    * 聊天ID
    */
    @ApiModelProperty(value="聊天ID")
    private Integer chatId;

    /**
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Integer ownerId;

    /**
    * 内容
    */
    @ApiModelProperty(value="内容")
    private String content;
}