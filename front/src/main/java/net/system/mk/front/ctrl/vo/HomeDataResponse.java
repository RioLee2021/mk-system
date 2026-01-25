package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.pojo.BrandInfo;
import net.system.mk.commons.pojo.ProductInfo;

import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 10:39
 */
@Data
public class HomeDataResponse {

    @ApiModelProperty(value = "品牌列表",notes = "最多8个")
    private List<BrandInfo> brands;

    @ApiModelProperty(value = "产品列表",notes = "最多20个")
    private List<ProductInfo> products;


}
