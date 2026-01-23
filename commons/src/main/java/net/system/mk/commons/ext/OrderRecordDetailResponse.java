package net.system.mk.commons.ext;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.conf.MyLocalDateTimeDeserializer;
import net.system.mk.commons.conf.MyLocalDateTimeSerializer;
import net.system.mk.commons.pojo.OrderPdRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 11:49
 */
@Data
public class OrderRecordDetailResponse {

    @ApiModelProperty(value = "发起用户ID")
    private Integer ownerId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = MyLocalDateTimeSerializer.class, deserializeUsing = MyLocalDateTimeDeserializer.class)
    @JsonSerialize(using = MyLocalDateTimeSerializer.class)
    @JsonDeserialize(using = MyLocalDateTimeDeserializer.class)
    private LocalDateTime registerAt;

    @ApiModelProperty(value = "产品ID")
    private Integer productId;

    @ApiModelProperty(value = "图片1地址")
    private String pic1Url;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "标签价格")
    private BigDecimal labelPrice;

    @ApiModelProperty(value = "拼单价格")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "佣金")
    private BigDecimal commission;

    @ApiModelProperty(value = "上架时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "拼单参与记录")
    private List<OrderPdRecord> pdRecords;
}
