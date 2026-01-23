package net.system.mk.backend.ctrl.operator.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 0:18
 */
@Data
public class SendChatRequest {

    @ApiModelProperty(value = "聊天ID",required = true)
    @NotNull(message = "聊天ID不能为空")
    private Integer chatId;

    @ApiModelProperty(value = "内容",required = true)
    @NotBlank(message = "内容不能为空")
    private String content;
}
