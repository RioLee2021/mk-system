package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客服聊天日志
 */
@ApiModel(description="客服聊天日志")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class CustomerChat extends BasePO {
    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 客服ID
    */
    @ApiModelProperty(value="客服ID")
    private Integer customerId;

    /**
    * 回复标识
    */
    @ApiModelProperty(value="回复标识")
    private Boolean replyFlg;
}