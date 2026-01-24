package net.system.mk.commons.ext;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;
import net.system.mk.commons.pojo.ChatMsgLog;
import net.system.mk.commons.pojo.MbrInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 0:05
 */
@Data
@Accessors(chain = true)
public class CustomerChatDetail {

    @ApiModelProperty("聊天ID")
    private Integer chatId;

    @ApiModelProperty("会员ID")
    private Integer mbrId;

    @ApiModelProperty("会员余额")
    private BigDecimal balance;

    @ApiModelProperty("会员手机")
    private String phone;

    @ApiModelProperty("会员总充值")
    private BigDecimal totalRecharge;

    @ApiModelProperty("会员今日充值")
    private BigDecimal dailyRecharge;

    @ApiModelProperty("会员总提现")
    private BigDecimal totalWithdraw;

    @ApiModelProperty("会员今日提现")
    private BigDecimal dailyWithdraw;

    @ApiModelProperty("会员注册IP")
    private String registerIp;

    @ApiModelProperty("会员注册地区")
    private String registerRegion;

    @ApiModelProperty("会员注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class, deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime crateAt;

    @ApiModelProperty("会员最后登录ip")
    private String lastLoginIp;

    @ApiModelProperty("会员最后登录地区")
    private String loginRegion;

    @ApiModelProperty("会员最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class, deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime lastLoginAt;

    @ApiModelProperty("会员关系路径")
    private String relationshipRoute;

    @ApiModelProperty("聊天记录")
    private List<ChatMsgLog> chatMsgLogs;

}
