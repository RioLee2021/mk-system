package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.pojo.OrderPdRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:14
 */
@Mapper
public interface OrderPdRecordMapper extends BaseMapper<OrderPdRecord> {

    @Select("select opr.*,mi.vip_level,mi.phone from order_pd_record opr left join mbr_info mi on opr.mbr_id = opr.id where opr.order_no = #{orderNo} order by id")
    List<OrderPdRecord> getListByOrderNo(@Param("orderNo") String orderNo);

    @Select("select opr.*,mi.vip_level,mi.phone from order_pd_record opr inner join mbr_info mi on opr.mbr_id = opr.id ${ew.customSqlSegment}")
    IPage<OrderPdRecord> getPageByEw(IPage<OrderPdRecord> page, @Param("ew")QueryWrapper<Object> ew);
}
