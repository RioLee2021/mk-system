package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.system.mk.commons.anno.QueryCon;

import java.time.LocalDateTime;

/**
 * @author USER
 * @date 2025-06-2025/6/30/0030 20:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TimeRangePageRequest extends PagerRequest{

    @ApiModelProperty(name = "开始时间")
    @QueryCon(name = "create_at",op=QOP.ge)
    private LocalDateTime startTime;

    @ApiModelProperty(name = "结束时间")
    @QueryCon(name = "create_at",op=QOP.le)
    private LocalDateTime endTime;
}
