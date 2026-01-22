package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员资产表
 */
@ApiModel(description="会员资产表")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MbrAssets extends BasePO {
    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 会员余额
    */
    @ApiModelProperty(value="会员余额")
    private BigDecimal balance;

    /**
    * 会员积分
    */
    @ApiModelProperty(value="会员积分")
    private Integer integral;
}