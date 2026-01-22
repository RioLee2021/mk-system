package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 品牌信息
 */
@ApiModel(description="品牌信息")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class BrandInfo extends BasePO {
    /**
    * 品牌名称
    */
    @ApiModelProperty(value="品牌名称")
    private String brandName;

    /**
    * 品牌logo图片地址
    */
    @ApiModelProperty(value="品牌logo图片地址")
    private String brandLogoUrl;

    /**
    * 排序字段
    */
    @ApiModelProperty(value="排序字段")
    private Integer brandSort;
}