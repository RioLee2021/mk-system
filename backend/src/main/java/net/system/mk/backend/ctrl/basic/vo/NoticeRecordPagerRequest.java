package net.system.mk.backend.ctrl.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.meta.PagerRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeRecordPagerRequest extends PagerRequest {

    @ApiModelProperty(value = "公告ID/用户ID")
    private Integer id;
}
