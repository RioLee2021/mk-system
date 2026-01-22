package net.system.mk.commons.meta;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author USER
 */
@Data
public class DictResponse implements Serializable {

    @ApiModelProperty(value = "字典名称",notes = "对应字典类型上备注的类名")
    private String dictName;

    private List<DictItem> items = Lists.newArrayList();

    public void addItem(DictItem item){
        items.add(item);
    }
}
