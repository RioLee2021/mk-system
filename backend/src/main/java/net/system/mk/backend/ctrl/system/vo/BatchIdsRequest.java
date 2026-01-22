package net.system.mk.backend.ctrl.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:19
 */
@Data
public class BatchIdsRequest {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
    private List<Integer> ids;
}
