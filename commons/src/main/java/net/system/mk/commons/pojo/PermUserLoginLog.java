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
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;

/**
 * 管理员登录日志
 */
@ApiModel(description="管理员登录日志")
@Data
@Accessors(chain = true)
public class PermUserLoginLog {

    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    @TableField(fill = FieldFill.INSERT ,value = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    /**
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Integer permUserId;

    /**
    * 登录IP
    */
    @ApiModelProperty(value="登录IP")
    private String fromIp;

    /**
    * 登录地区
    */
    @ApiModelProperty(value="登录地区")
    private String fromRegion;

    /**
    * 登录UA
    */
    @ApiModelProperty(value="登录UA")
    private String fromUa;
}