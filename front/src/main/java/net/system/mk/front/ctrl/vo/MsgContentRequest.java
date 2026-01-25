package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 13:26
 */
@Data
public class MsgContentRequest {

    @ApiModelProperty(value = "聊天id", required = true)
    @NotNull(message = "chatId can not be null")
    private Integer chatId;

    @ApiModelProperty(value = "消息内容", required = true)
    @NotBlank(message = "message can not be blank")
    private String content;
}
