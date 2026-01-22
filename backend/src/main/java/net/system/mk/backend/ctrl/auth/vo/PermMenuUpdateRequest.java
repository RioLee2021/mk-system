package net.system.mk.backend.ctrl.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.BaseUpdateRequest;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 21:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PermMenuUpdateRequest extends BaseUpdateRequest {

    @ApiModelProperty(value = "图标",notes = "不超过100个字符串")
    @NotBlank(message = "图标不能为空")
    @Length(max = 100,message = "图标不能超过100个字符串")
    private String icon;

    @ApiModelProperty(value = "排序号",notes = "排序号")
    @NotNull(message = "排序号不能为空")
    private Integer sortNo;
}
