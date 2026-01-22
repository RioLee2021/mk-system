package net.system.mk.backend.ctrl.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermUserPagerRequest extends PagerRequest {

    @ApiModelProperty(value = "账号")
    @QueryCon
    private String account;

    @ApiModelProperty(value = "角色类型")
    @QueryCon
    private RoleType roleType;

    @ApiModelProperty(value = "商户ID",notes = "Options下拉,0为总平台")
    @QueryCon
    private Integer merchantId;
}
