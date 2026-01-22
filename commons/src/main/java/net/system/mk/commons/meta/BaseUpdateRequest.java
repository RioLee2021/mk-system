package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2025-11-2025/11/16/0016 8:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseUpdateRequest extends PermLoggedRequest{

    @ApiModelProperty(required = true,value = "id")
    @NotNull(message = "id不能为空")
    @Min(value = 1,message = "id不能小于1")
    private Integer id;
}
