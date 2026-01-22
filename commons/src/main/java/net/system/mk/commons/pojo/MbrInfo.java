package net.system.mk.commons.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.MbrStatus;
import net.system.mk.commons.enums.MbrType;
import net.system.mk.commons.enums.VipLevel;

/**
 * 会员基础信息表
 */
@ApiModel(description="会员基础信息表")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class MbrInfo extends BasePO {
    /**
    * 商户ID
    */
    @ApiModelProperty(value="商户ID")
    private Integer merchantId;

    /**
    * 会员登录密码
    */
    @ApiModelProperty(value="会员登录密码")
    private String loginPassword;

    /**
    * 会员提现密码
    */
    @ApiModelProperty(value="会员提现密码")
    private String withdrawPassword;

    /**
    * 会员账号
    */
    @ApiModelProperty(value="会员账号")
    private String account;

    /**
    * 会员昵称
    */
    @ApiModelProperty(value="会员昵称")
    private String nickname;

    /**
    * 会员手机号
    */
    @ApiModelProperty(value="会员手机号")
    private String phone;

    /**
    * 会员邀请码
    */
    @ApiModelProperty(value="会员邀请码")
    private String inviteCode;

    /**
    * 会员当前令牌
    */
    @ApiModelProperty(value="会员当前令牌")
    private String currToken;

    /**
    * 会员最后登录时间
    */
    @ApiModelProperty(value="会员最后登录时间")
    private LocalDateTime lastLoginAt;

    /**
    * 会员注册IP
    */
    @ApiModelProperty(value="会员注册IP")
    private String registerIp;

    /**
    * 会员注册IP所属区域
    */
    @ApiModelProperty(value="会员注册IP所属区域")
    private String registerRegion;

    /**
    * 会员登录IP
    */
    @ApiModelProperty(value="会员登录IP")
    private String loginIp;

    /**
    * 会员登录IP所属区域
    */
    @ApiModelProperty(value="会员登录IP所属区域")
    private String loginRegion;

    /**
    * 会员状态(MbrStatus)
    */
    @ApiModelProperty(value="会员状态(MbrStatus)")
    private MbrStatus status;

    /**
    * 会员上级ID
    */
    @ApiModelProperty(value="会员上级ID")
    private Integer parentId;

    /**
    * 会员VIP等级(VipLevel)
    */
    @ApiModelProperty(value="会员VIP等级(VipLevel)")
    private VipLevel vipLevel;

    /**
    * 会员类型(MbrType)
    */
    @ApiModelProperty(value="会员类型(MbrType)")
    private MbrType mbrType;

    /**
    * 会员虚拟会员标识
    */
    @ApiModelProperty(value="会员虚拟会员标识")
    private Boolean virtualFlg;

    /**
    * 会员CP标识
    */
    @ApiModelProperty(value="会员CP标识")
    private String cpMark;

    /**
    * 会员关系路径
    */
    @ApiModelProperty(value="会员关系路径")
    private String relationshipRoute;

    /**
    * 会员真实姓名
    */
    @ApiModelProperty(value="会员真实姓名")
    private String actualName;

    /**
    * 会员银行名称
    */
    @ApiModelProperty(value="会员银行名称")
    private String bankName;

    /**
    * 会员银行卡号
    */
    @ApiModelProperty(value="会员银行卡号")
    private String bankCardNo;

    /**
    * 会员客服
    */
    @ApiModelProperty(value="会员客服")
    private String customerAccount;

    /**
    * 会员logo编号
    */
    @ApiModelProperty(value="会员logo编号")
    private String logoNumber;

    /**
    * 会员每日订单限制
    */
    @ApiModelProperty(value="会员每日订单限制")
    private Integer dailyOrderLimit;

    // 非数据库字段

    @ApiModelProperty(value="会员余额")
    @TableField(exist = false)
    private BigDecimal balance;

    @ApiModelProperty(value="会员积分")
    @TableField(exist = false)
    private Integer integral;

    @ApiModelProperty(value="会员累计充值")
    @TableField(exist = false)
    private BigDecimal totalRecharge;

    @ApiModelProperty(value="会员累计提现")
    @TableField(exist = false)
    private BigDecimal totalWithdraw;

    @ApiModelProperty(value="会员上级手机号")
    @TableField(exist = false)
    private String parentPhone;

    @ApiModelProperty(value="会员相同IP登录次数")
    @TableField(exist = false)
    private Integer sameLoginCnt;

    @ApiModelProperty(value="会员相同IP注册次数")
    @TableField(exist = false)
    private Integer sameRegCnt;
}