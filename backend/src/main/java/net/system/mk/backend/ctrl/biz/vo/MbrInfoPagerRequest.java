package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.MbrType;
import net.system.mk.commons.enums.VipLevel;
import net.system.mk.commons.meta.PagerRequest;
import net.system.mk.commons.meta.QOP;

import java.time.LocalDateTime;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrInfoPagerRequest extends PagerRequest {

    @ApiModelProperty(hidden = true)
    @QueryCon
    private Integer merchantId;


    @ApiModelProperty(value = "手机号/邀请码/账号/用户ID")
    private Object cond;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "相同IP")
    private Boolean sameIpOnly;

    @ApiModelProperty(value = "注册时间开始")
    @QueryCon(name = "create_at", op = QOP.ge)
    private LocalDateTime regStart;

    @ApiModelProperty(value = "注册时间结束")
    @QueryCon(name = "create_at", op = QOP.le)
    private LocalDateTime regEnd;

    @ApiModelProperty(value = "登录时间开始")
    @QueryCon(name = "last_login_at", op = QOP.ge)
    private LocalDateTime loginStart;

    @ApiModelProperty(value = "登录时间结束")
    @QueryCon(name = "last_login_at", op = QOP.le)
    private LocalDateTime loginEnd;

    @ApiModelProperty(value = "虚拟会员")
    @QueryCon
    private Boolean virtualFlg;

    @ApiModelProperty(value = "会员类型", notes = "字典")
    @QueryCon
    private MbrType mbrType;

    @ApiModelProperty(value = "会员等级", notes = "字典")
    @QueryCon
    private VipLevel vipLevel;

    @ApiModelProperty(value = "CP标识")
    @QueryCon
    private String cpMark;
}
