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
 * 管理员操作日志
 */
@ApiModel(description="管理员操作日志")
@Data
@Accessors(chain = true)
public class PermUserOperationLog {

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
    * 操作IP
    */
    @ApiModelProperty(value="操作IP")
    private String fromIp;

    /**
    * 操作地区
    */
    @ApiModelProperty(value="操作地区")
    private String fromRegion;

    /**
    * 请求URI
    */
    @ApiModelProperty(value="请求URI")
    private String requestUri;

    /**
    * 操作动作
    */
    @ApiModelProperty(value="操作动作")
    private String action;

    /**
    * 操作内容
    */
    @ApiModelProperty(value="操作内容")
    private String actionContent;

    /**
    * 关联业务编号
    */
    @ApiModelProperty(value="关联业务编号")
    private String relatedNo;
}