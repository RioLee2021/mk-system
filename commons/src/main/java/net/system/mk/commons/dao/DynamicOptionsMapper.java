package net.system.mk.commons.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 10:55
 */
@Mapper
public interface DynamicOptionsMapper {

    @Select("select * from dynamic_tb where merchant_id = #{merchantId} order by id")
    List<JSONObject> selectMerchantOptions(@Param("merchantId") Integer merchantId);

    @Select("select * from dynamic_tb order by id")
    List<JSONObject> selectOptions();
}
