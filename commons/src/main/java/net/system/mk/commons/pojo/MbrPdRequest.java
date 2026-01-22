package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户拼单申请
 */
@ApiModel(description="用户拼单申请")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MbrPdRequest extends BasePO {
    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 订单编号
    */
    @ApiModelProperty(value="订单编号")
    private String orderNo;
}