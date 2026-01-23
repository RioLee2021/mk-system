package net.system.mk.commons.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户拼单申请
 */
@ApiModel(description = "用户拼单申请")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MbrPdRequest extends BasePO {
    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private Integer mbrId;

    /**
     * 拼单流水号
     */
    @ApiModelProperty(value = "拼单流水号")
    private String pdNo;

    //非数据库字段

    @ApiModelProperty(value = "手机号")
    @TableField(exist = false)
    private String phone;

    @ApiModelProperty(value = "参与金额")
    @TableField(exist = false)
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "佣金")
    @TableField(exist = false)
    private BigDecimal commission;


}