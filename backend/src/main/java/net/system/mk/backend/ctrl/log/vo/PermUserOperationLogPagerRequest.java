package net.system.mk.backend.ctrl.log.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.QOP;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermUserOperationLogPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(name = "账号")
    @QueryCon(name = "pu.account")
    private String account;

    @ApiModelProperty(name = "操作")
    @QueryCon
    private String action;

    @ApiModelProperty(name = "IP")
    @QueryCon(op = QOP.likeRight)
    private String fromIp;

    @ApiModelProperty(name = "请求地址")
    @QueryCon
    private String requestUri;

    @ApiModelProperty(name = "关联编号")
    @QueryCon
    private String relatedNo;
}
