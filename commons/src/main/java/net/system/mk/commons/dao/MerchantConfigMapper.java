package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.MerchantConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 10:29
 */
@Mapper
public interface MerchantConfigMapper extends BaseMapper<MerchantConfig> {

    @Select("select * from merchant_config where mch_code=#{mchCode}")
    MerchantConfig getByMchCode(@Param("mchCode") String mchCode);

    @Select("select * from merchant_config where disabled = false")
    List<MerchantConfig> allEnabledMerchant();
}
