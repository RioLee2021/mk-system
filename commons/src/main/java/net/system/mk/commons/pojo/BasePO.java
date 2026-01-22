package net.system.mk.commons.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;
import net.system.mk.commons.meta.BaseValidationBean;
import net.system.mk.commons.utils.DateTimeUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author USER
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasePO extends BaseValidationBean implements Serializable {

    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    @TableField(fill = FieldFill.INSERT ,value = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT_UPDATE ,value = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "启用状态：0-禁用，1-启用")
    private Boolean disabled;

    @TableField(fill = FieldFill.INSERT ,value = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy="System";

    public Integer dateNumber(){
        return Integer.parseInt(createAt.format(DateTimeUtils.YYYYMMDD));
    }
}
