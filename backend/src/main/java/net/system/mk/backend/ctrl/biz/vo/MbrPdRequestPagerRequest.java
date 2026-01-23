package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrPdRequestPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(value = "手机号")
    @QueryCon(name = "mi.phone")
    private String phone;

    @ApiModelProperty(value = "是否已处理")
    private Boolean done;
}
