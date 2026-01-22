package net.system.mk.commons.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.enums.RoleType;

/**
 * 管理员表
 */
@ApiModel(description="管理员表")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermUser extends BasePO implements IBaseContext {
    /**
    * 商户ID
    */
    @ApiModelProperty(value="商户ID")
    private Integer merchantId;

    /**
    * 账号
    */
    @ApiModelProperty(value="账号")
    private String account;

    /**
    * 密码
    */
    @ApiModelProperty(value="密码")
    private String password;

    /**
    * 角色类型(RoleType)
    */
    @ApiModelProperty(value="角色类型(RoleType)")
    private RoleType roleType;

    /**
    * 登录token
    */
    @ApiModelProperty(value="登录token")
    private String token;

    /**
    * otp验证码
    */
    @ApiModelProperty(value="otp验证码")
    private String otpCode;

    @Override
    public Integer id() {
        return this.getId();
    }

    @Override
    public String name() {
        return this.roleType.getChName();
    }

    @Override
    public String account() {
        return this.account;
    }

    @Override
    public String token() {
        return this.token;
    }

    @Override
    public boolean isRoot() {
        return this.getRoleType().ordinal()<=RoleType.WebMaster.ordinal();
    }

    @Override
    public String otp() {
        return this.otpCode;
    }

    @Override
    public Integer merchantId() {
        return this.merchantId;
    }

    @Override
    public boolean isBanned() {
        return this.getDisabled()!=null&&this.getDisabled();
    }

    @Override
    public RoleType role() {
        return this.roleType;
    }

    @Override
    public CtxScope scope() {
        return CtxScope.backend;
    }
}