package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户配置
 */
@ApiModel(description="商户配置")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MerchantConfig extends BasePO {
    /**
    * 商户编号
    */
    @ApiModelProperty(value="商户编号")
    private String mchCode;

    /**
    * 商户名称
    */
    @ApiModelProperty(value="商户名称")
    private String mchName;

    /**
    * 商户api白名单
    */
    @ApiModelProperty(value="商户api白名单")
    private String apiWhiteList;

    /**
    * 商户备注
    */
    @ApiModelProperty(value="商户备注")
    private String memo;
}