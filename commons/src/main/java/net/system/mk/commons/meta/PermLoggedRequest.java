package net.system.mk.commons.meta;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.ctx.IBaseContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @author USER
 * @date 2025-11-2025/11/15/0015 23:35
 */
@Data
public class PermLoggedRequest {

    @ApiModelProperty(hidden = true)
    protected String beforeMemo;

    @ApiModelProperty(hidden = true)
    protected String afterMemo;

    @ApiModelProperty(hidden = true)
    protected String allMemo;

    @ApiModelProperty(hidden = true)
    protected IBaseContext operator;

    @ApiModelProperty(hidden = true)
    protected Object relatedNo;

    public void loadBeforeAndAfter(Object before, Object after) {
        //获取需要比较的字段
        List<FieldProperty> ps = getFieldProperties(after);
        BeanWrapper bw = new BeanWrapperImpl(before);
        PropertyDescriptor[] pds = bw.getPropertyDescriptors();
        StringBuilder bfe = new StringBuilder();
        StringBuilder afe = new StringBuilder();
        for (PropertyDescriptor pd : pds){
            String pdName = pd.getName();
            Object value = bw.getPropertyValue(pdName);
            for (FieldProperty p : ps){
                if (pdName.equals(p.getName())&&!Objects.equals(value,p.getValue())){
                    bfe.append(p.getDesc()).append(":").append(value==null?"NULL":value).append(",");
                    afe.append(p.getDesc()).append(":").append(p.getValue()==null?"NULL":p.getValue()).append(",");
                }
            }
        }
        beforeMemo = bfe.toString();
        afterMemo = afe.toString();
    }

    private List<FieldProperty> getFieldProperties(Object obj) {
        List<FieldProperty> rs = Lists.newArrayList();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            try {
                if (field.isAnnotationPresent(ApiModelProperty.class)){
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    ApiModelProperty ann = field.getAnnotation(ApiModelProperty.class);
                    FieldProperty item = new FieldProperty();
                    item.setName(field.getName());
                    item.setValue(value);
                    item.setDesc(ann.value());
                    rs.add(item);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

}
