package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.pojo.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 18:02
 */
@Mapper
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    @Select("select id as value, concat('(',order_price,')',product_name) as label, order_price as sort_filed from product_info order by order_price ")
    List<DictItem> productOps();
}
