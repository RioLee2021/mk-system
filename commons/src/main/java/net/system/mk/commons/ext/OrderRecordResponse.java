package net.system.mk.commons.ext;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;
import net.system.mk.commons.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 10:42
 */
@Data
public class OrderRecordResponse {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户ID")
    private Integer ownerId;

    @ApiModelProperty(value = "参与用户ID列表")
    private String joinerIds;

    @ApiModelProperty(value = "拼单金额")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "佣金")
    private BigDecimal commission;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class,deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime createAt;

    @ApiModelProperty(value = "订单状态")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "回购人数")
    private Integer repurchaseCnt;
}
