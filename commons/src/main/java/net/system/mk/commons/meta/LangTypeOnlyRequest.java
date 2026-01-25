package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.LangType;

import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 10:33
 */
@Data
public class LangTypeOnlyRequest {

    @ApiModelProperty(value = "语言类型",required = true)
    @NotNull
    private LangType langType;
}
