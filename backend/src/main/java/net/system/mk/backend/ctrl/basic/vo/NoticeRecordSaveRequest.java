package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.enums.LangType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:55
 */
@Data
public class NoticeRecordSaveRequest {

    @ApiModelProperty(value = "公告ID")
    private Integer id;

    /**
     * 会员ID
     */
    @ApiModelProperty(value="会员ID",required = true)
    @NotNull(message = "会员ID不能为空")
    private Integer mbrId;

    /**
     * 语言类型(LangType)
     */
    @ApiModelProperty(value="语言类型(LangType)",required = true)
    @NotNull(message = "语言类型(LangType)不能为空")
    private LangType langType;

    /**
     * 富文本内容
     */
    @ApiModelProperty(value="富文本内容",required = true)
    @NotBlank(message = "富文本内容不能为空")
    private String richTxt;

    @ApiModelProperty(value = "是否禁用",required = true)
    @NotNull(message = "是否禁用不能为空")
    private Boolean disabled;
}
