package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.pojo.MbrPdRequest;
import net.system.mk.commons.pojo.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:45
 */
@Mapper
public interface MbrPdRequestMapper extends BaseMapper<MbrPdRequest> {

    @Select("select mpr.*,mi.phone,opr.order_price,opr.commission from mbr_pd_request mpr inner join mbr_info mi on mpr.mbr_id = mi.id left join order_pd_record opr on mpr.pd_no = opr.pd_no ${ew.customSqlSegment}")
    IPage<MbrPdRequest> getPageByEw(@Param("page") IPage<MbrPdRequest> page, @Param("ew") QueryWrapper<Object> ew);

    @Select("select count(1) from mbr_pd_request where pd_no is null")
    Integer getRunningCnt();

    @Select("select count(1) from mbr_pd_request where mbr_id = #{mbrId} and pd_no is null")
    int cntRequestingByMbrId(@Param("mbrId") Integer mbrId);


}
