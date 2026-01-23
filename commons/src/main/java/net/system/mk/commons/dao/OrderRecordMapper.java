package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.ext.OrderRecordResponse;
import net.system.mk.commons.pojo.OrderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 20:19
 */
@Mapper
public interface OrderRecordMapper extends BaseMapper<OrderRecord> {


    @Select("select ord.order_no,ord.owner_id,pi.order_price,pi.commission,ord.create_at, group_concat(opr.mbr_id,',') as joiner_ids, count(opr.id) as total_cnt, count(opr2.id) as repurchase_cnt , (count(opr.id)-count(opr2.id)=0) as order_status" +
            " from order_record ord inner join product_info pi on ord.product_id = pi.id left join order_pd_record opr on opr.order_no = ord.order_no left join order_pd_record opr2 on opr.order_no = ord.order_no and opr2.pd_status > 0 ${ew.customSqlSegment}")
    IPage<OrderRecordResponse> getPageByEw(IPage<OrderRecord> page, @Param("ew")QueryWrapper<Object> ew);
}
