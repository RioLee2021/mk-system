package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.BaseUpdateRequest;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrCpMarkUpdRequest extends BaseUpdateRequest {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(value = "CP值", required = true)
    @NotBlank(message = "CP值不能为空")
    private String cpMark;
}
