package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:55
 */
@Data
public class BrandInfoSaveRequest {

    @ApiModelProperty(value = "品牌ID")
    private Integer id;

    /**
     * 品牌名称
     */
    @ApiModelProperty(value="品牌名称",required = true)
    @NotBlank(message = "品牌名称不能为空")
    private String brandName;

    /**
     * 品牌logo图片地址
     */
    @ApiModelProperty(value="品牌logo图片地址",required = true)
    @NotBlank(message = "品牌logo图片地址不能为空")
    private String brandLogoUrl;

    /**
     * 排序字段
     */
    @ApiModelProperty(value="排序字段",required = true)
    @NotNull(message = "排序字段不能为空")
    private Integer brandSort;
}
