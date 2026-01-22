package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author USER
 * @date 2025-11-2025/11/18/0018 10:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OtpUpdateRequest extends BaseUpdateRequest{

    @ApiModelProperty(required = true,value = "otp")
    @NotBlank
    @Pattern(regexp = "^\\d{6}$")
    private String otp;
}
