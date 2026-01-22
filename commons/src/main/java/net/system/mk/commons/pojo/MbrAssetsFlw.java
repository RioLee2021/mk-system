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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;
import net.system.mk.commons.enums.AssetsFlwType;

/**
 * 会员资金流水
 */
@ApiModel(description="会员资金流水")
@Data
@Accessors(chain = true)
public class MbrAssetsFlw {

    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    @TableField(fill = FieldFill.INSERT ,value = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT ,value = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy="System";

    /**
    * 流水编号
    */
    @ApiModelProperty(value="流水编号")
    private String flwNo;

    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 金额
    */
    @ApiModelProperty(value="金额")
    private BigDecimal amount;

    /**
    * 流水类型(AssetsFlwType)
    */
    @ApiModelProperty(value="流水类型(AssetsFlwType)")
    private AssetsFlwType type;

    /**
    * 流水前金额
    */
    @ApiModelProperty(value="流水前金额")
    private BigDecimal beforeAmt;

    /**
    * 流水后金额
    */
    @ApiModelProperty(value="流水后金额")
    private BigDecimal afterAmt;

    /**
    * 流水备注
    */
    @ApiModelProperty(value="流水备注")
    private String remark;

    /**
    * 关联编号
    */
    @ApiModelProperty(value="关联编号")
    private String relatedNo;

    /**
    * 关联ID
    */
    @ApiModelProperty(value="关联ID")
    private Integer relatedId;
}