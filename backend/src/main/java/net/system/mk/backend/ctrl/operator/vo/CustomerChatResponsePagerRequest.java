package net.system.mk.backend.ctrl.operator.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 22:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerChatResponsePagerRequest extends PagerRequest {

    @ApiModelProperty(value = "会员账号")
    @QueryCon(name = "mi.account")
    private String mbrAccount;

    @ApiModelProperty(hidden = true)
    @QueryCon
    private Integer customerId;
}
