package net.system.mk.backend.ctrl.biz.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.meta.TimeRangePageRequest;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MbrAssetsFlwPagerRequest extends TimeRangePageRequest {

    @ApiModelProperty(value = "资产流水类型",notes = "字典")
    @QueryCon
    private AssetsFlwType type;

    @ApiModelProperty(value = "会员",notes = "用户账号/用户ID/用户电话")
    private Object member;

    @ApiModelProperty(value = "关联用户ID")
    @QueryCon
    private Integer relatedId;

    @ApiModelProperty(value = "关联流水号")
    @QueryCon
    private String relatedNo;


}
