package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author USER
 * @date 2025-03-2025/3/14/0014 5:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictItem implements Serializable {

    @ApiModelProperty(value="字典值")
    private Object value;
    @ApiModelProperty(value="展示名称")
    private String label;
    @ApiModelProperty(value="排序字段")
    private Object sortFiled=0;

    public DictItem(Object value, String label) {
        this.value = value;
        this.label = label;
    }
}
