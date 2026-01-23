package net.system.mk.backend.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:26
 */
@Data
public class CurrentTaskCnt {

    @ApiModelProperty(value = "客服消息待处理数")
    private Integer chatCnt;

    @ApiModelProperty(value = "提现待处理数")
    private Integer withdrawCnt;

    @ApiModelProperty(value = "拼单申请待处理数")
    private Integer pdReqCnt;
}
