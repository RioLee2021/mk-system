package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.enums.MbrStatus;
import net.system.mk.commons.meta.BaseUpdateRequest;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeamStatusUpdRequest extends BaseUpdateRequest {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(value = "会员状态(MbrStatus)",required = true)
    @NotNull(message = "会员状态不能为空")
    private MbrStatus status;
}
