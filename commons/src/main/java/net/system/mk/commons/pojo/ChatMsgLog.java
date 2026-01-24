package net.system.mk.commons.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;

import java.time.LocalDateTime;

/**
 * 聊天日志
 */
@ApiModel(description = "聊天日志")
@Data
@Accessors(chain = true)
public class ChatMsgLog{

    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    @TableField(fill = FieldFill.INSERT ,value = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "启用状态：0-禁用，1-启用")
    private Boolean disabled;

    /**
     * 聊天ID
     */
    @ApiModelProperty(value = "聊天ID")
    private Integer chatId;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Integer ownerId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 客服标识
     */
    @ApiModelProperty(value = "客服标识")
    private Boolean customerFlg;

    /**
     * 图片标识
     */
    @ApiModelProperty(value = "图片标识")
    private Boolean imageFlg;
}