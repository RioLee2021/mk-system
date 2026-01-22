package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.MbrStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 14:30
 */
@Data
public class BatchChangeMbrStatusUpdRequest {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(value = "会员状态",required = true)
    @NotNull(message = "会员状态不能为空")
    private MbrStatus status;

    @ApiModelProperty(value = "会员ID列表",required = true)
    @NotNull(message = "会员ID列表不能为空")
    private List<Integer> ids;
}
