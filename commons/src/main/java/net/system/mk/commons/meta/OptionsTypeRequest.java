package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.OptionsType;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 10:53
 */
@Data
public class OptionsTypeRequest {

    @ApiModelProperty(value = "选项类型",required = true)
    @NotNull(message = "选项类型不能为空")
    private OptionsType type;
}
