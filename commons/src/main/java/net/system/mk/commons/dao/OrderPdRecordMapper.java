package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.enums.OrderPdStatus;
import net.system.mk.commons.meta.ProductOrderResponse;
import net.system.mk.commons.pojo.OrderPdRecord;
import net.system.mk.commons.pojo.ProductInfo;
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

    @Select("select opr.*,mi.vip_level,mi.phone from order_pd_record opr inner join mbr_info mi on opr.mbr_id = mi.id ${ew.customSqlSegment}")
    IPage<OrderPdRecord> getPageByEw(IPage<OrderPdRecord> page, @Param("ew")QueryWrapper<Object> ew);

    @Select("select pi.id,pi.product_name,pi.pic1_url,pi.pic2_url,pi.pic3_url,pi.label_price,opr.order_price,opr.commission,pi.product_desc,pi.special_offer,opr.order_no from order_pd_record opr inner join order_record ord on ord.order_no = opr.order_no inner join product_info pi on ord.product_id = pi.id  where pd_status = 0 and mbr_id = #{mbrId} limit 1")
    ProductInfo getRunningPdProductByMbrId(@Param("mbrId")Integer mbrId);

    @Select("select opr.* from order_pd_record opr inner join order_record ord on ord.order_no = opr.order_no where pd_status = 0 and mbr_id = #{mbrId} and ord.product_id = #{productId} limit 1")
    OrderPdRecord getPdRecordByMbrIdAndProductId(@Param("mbrId")Integer mbrId,@Param("productId")Integer productId);

    @Select("select opr.*,pi.product_name,pi.pic1_url,pi.pic2_url,pi.pic3_url,pi.label_price,pi.product_desc,pi.special_offer from order_pd_record opr inner join order_record ord on ord.order_no = opr.order_no inner join product_info pi on ord.product_id = pi.id where opr.mbr_id = #{mbrId} and opr.pd_status = #{pdStatus}")
    IPage<ProductOrderResponse> getProductOrderResponsePageByMbrIdAndPdStatus(IPage<ProductOrderResponse> page, @Param("mbrId")Integer mbrId, @Param("pdStatus") OrderPdStatus pdStatus);

    @Select("select opr.*,pi.product_name,pi.pic1_url,pi.pic2_url,pi.pic3_url,pi.label_price,pi.product_desc,pi.special_offer from order_pd_record opr inner join order_record ord on ord.order_no = opr.order_no inner join product_info pi on ord.product_id = pi.id where opr.mbr_id = #{mbrId} and opr.pd_status >= #{pdStatus} and opr.pd_status<= #{pdStatus2}")
    IPage<ProductOrderResponse> getProductOrderResponsePageByMbrIdAndPdStatusRange(IPage<ProductOrderResponse> page, @Param("mbrId")Integer mbrId, @Param("pdStatus") OrderPdStatus pdStatus, @Param("pdStatus2") OrderPdStatus pdStatus2);
}
