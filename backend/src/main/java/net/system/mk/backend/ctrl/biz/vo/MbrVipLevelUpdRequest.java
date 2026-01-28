package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.enums.VipLevel;
import net.system.mk.commons.meta.BaseUpdateRequest;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/28/0028 14:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrVipLevelUpdRequest extends BaseUpdateRequest {

    @ApiModelProperty(required = true,value = "会员等级")
    @NotNull(message = "会员等级不能为空")
    private VipLevel vipLevel;
}
