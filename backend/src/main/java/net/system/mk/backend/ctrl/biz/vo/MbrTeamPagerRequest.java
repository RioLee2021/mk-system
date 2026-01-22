package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrTeamPagerRequest extends MbrInfoPagerRequest{

    @ApiModelProperty(value = "当前会员ID",notes = "由上级带过来的",required = true)
    @NotNull(message = "当前会员ID不能为空")
    private Integer currentId;

    @ApiModelProperty(value = "团队等级",notes = "0-7，所有下级则为0")
    private Integer level;


}
