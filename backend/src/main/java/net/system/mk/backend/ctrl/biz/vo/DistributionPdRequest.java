package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.BaseUpdateRequest;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 18:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DistributionPdRequest extends BaseUpdateRequest {

    @ApiModelProperty(value = "产品ID",required = true)
    @NotNull(message = "产品ID不能为空")
    private Integer productId;
}
