package net.system.mk.front.ctx;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.enums.*;
import net.system.mk.commons.pojo.MbrAssets;
import net.system.mk.commons.pojo.MbrInfo;

import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 9:30
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberContext implements IBaseContext {

    @ApiModelProperty(hidden = true)
    private Integer merchantId;

    @ApiModelProperty(hidden = true)
    private Integer parentId;

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("当前token")
    private String currToken;

    @ApiModelProperty("用户状态")
    private MbrStatus status;

    @ApiModelProperty("会员等级")
    private VipLevel vipLevel;

    @ApiModelProperty("会员类型")
    private MbrType  mbrType;

    @ApiModelProperty("真实姓名")
    private String actualName;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("银行卡号")
    private String bankCardNo;

    @ApiModelProperty("头像号码")
    private String logoNumber;

    @ApiModelProperty("余额")
    private BigDecimal balance;

    @ApiModelProperty("积分")
    private Integer integral;

    @ApiModelProperty("今日收益")
    private BigDecimal todayEarnings;

    @ApiModelProperty("累计收益")
    private BigDecimal totalRevenue;

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public String name() {
        return nickname;
    }

    @Override
    public String account() {
        return account;
    }

    @Override
    public String token() {
        return currToken;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public String otp() {
        return null;
    }

    @Override
    public Integer merchantId() {
        return merchantId;
    }

    @Override
    public boolean isBanned() {
        return status==MbrStatus.banned;
    }

    @Override
    public RoleType role() {
        return RoleType.Member;
    }

    @Override
    public CtxScope scope() {
        return CtxScope.wap;
    }

    public void loadMbrInfo(MbrInfo mb){
        BeanUtil.copyProperties(mb,this);
    }

    public void loadMbrAssets(MbrAssets ma){
        balance = ma.getBalance();
        integral = ma.getIntegral();
    }

}
