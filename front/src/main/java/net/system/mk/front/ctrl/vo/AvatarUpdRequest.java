package net.system.mk.front.ctrl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 12:55
 */
@Data
public class AvatarUpdRequest {

    @ApiModelProperty(value = "头像",notes = "前端写死",required = true)
    @NotBlank(message = "avatar can not be null")
    private String avatar;
}
