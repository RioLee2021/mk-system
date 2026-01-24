package net.system.mk.commons.ext;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 22:45
 */
@Data
public class CustomerChatResponse {

    @ApiModelProperty("聊天ID")
    private Integer chatId;

    @ApiModelProperty("会员ID")
    private Integer mbrId;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("消息内容")
    private String msgContent;

    @ApiModelProperty("回复内容")
    private String replyContent;

    @ApiModelProperty("发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class, deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime sendTime;

    @ApiModelProperty("回复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class, deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime replyTime;

    @ApiModelProperty("客户最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty("是否回复")
    private Boolean replyFlg;

}
