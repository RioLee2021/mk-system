package net.system.mk.commons.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.system.mk.commons.enums.LangType;

/**
 * 公告管理
 */
@ApiModel(description="公告管理")
@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
public class NoticeRecord extends BasePO {
    /**
    * 会员ID
    */
    @ApiModelProperty(value="会员ID")
    private Integer mbrId;

    /**
    * 语言类型(LangType)
    */
    @ApiModelProperty(value="语言类型(LangType)")
    private LangType langType;

    /**
    * 富文本内容
    */
    @ApiModelProperty(value="富文本内容")
    private String richTxt;
}