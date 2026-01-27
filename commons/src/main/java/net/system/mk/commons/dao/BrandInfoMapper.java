package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.pojo.BrandInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:47
 */
@Mapper
public interface BrandInfoMapper extends BaseMapper<BrandInfo> {

    @Select("select bi.id as value ,concat(bi.brand_name,'(',count(pi.id),')') as label from brand_info bi left join product_info pi on bi.id = pi.brand_id and pi.disabled = false group by bi.id order by bi.id")
    List<DictItem> brandOps();
}
