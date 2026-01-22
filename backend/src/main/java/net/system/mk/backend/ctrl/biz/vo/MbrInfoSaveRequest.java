package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.enums.VipLevel;
import net.system.mk.commons.meta.PermLoggedRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 13:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrInfoSaveRequest extends PermLoggedRequest {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(value = "会员昵称",required = true)
    @NotBlank(message = "会员昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "会员手机号",required = true)
    @NotBlank(message = "会员手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "会员上级id",required = true)
    @NotNull(message = "会员上级id不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "会员等级",required = true,notes = "字典")
    @NotNull(message = "会员等级不能为空")
    private VipLevel vipLevel;

    @ApiModelProperty(value = "会员日订单限制",required = true)
    @NotNull(message = "会员日订单限制不能为空")
    private Integer dailyOrderLimit;

    @ApiModelProperty(value = "会员银行名称",notes = "下拉(前台写死)",required = true)
    @NotBlank(message = "会员银行名称不能为空")
    private String bankName;

    @ApiModelProperty(value = "会员银行卡号",required = true)
    @NotBlank(message = "会员银行卡号不能为空")
    private String bankCardNo;

    @ApiModelProperty(value = "真实姓名",required = true)
    @NotBlank(message = "真实姓名不能为空")
    private String actualName;

    @ApiModelProperty(value = "登录密码",required = true)
    @NotBlank(message = "登录密码不能为空")
    private String loginPassword;

    @ApiModelProperty(value = "出款密码",required = true)
    @NotBlank(message = "出款密码不能为空")
    private String withdrawPassword;

    @ApiModelProperty(value = "虚拟会员",required = true)
    @NotNull(message = "虚拟会员不能为空")
    private Boolean virtualFlg;



}
