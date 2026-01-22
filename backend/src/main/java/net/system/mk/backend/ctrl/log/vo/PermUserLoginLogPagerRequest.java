package net.system.mk.backend.ctrl.log.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.QOP;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermUserLoginLogPagerRequest extends TimeRangePageRequest {


    @ApiModelProperty("账号")
    @QueryCon(name = "pu.account")
    private String account;

    @ApiModelProperty("IP")
    @QueryCon(op = QOP.likeRight)
    private String fromIp;
}
