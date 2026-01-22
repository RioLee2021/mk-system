package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.enums.MbrStatus;
import net.system.mk.commons.pojo.MbrInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:27
 */
@Mapper
public interface MbrInfoMapper extends BaseMapper<MbrInfo> {

    @Select("select mb.*,ma.balance,ma.integral,ifnull(sum(mrr.amount),0) as total_recharge,ifnull(sum(mwr.amount),0) as total_withdraw,pmb.phone as parent_phone ,ifnull(login_stat.cnt,0) as same_login_cnt ,ifnull(reg_stat.cnt,0) as same_reg_cnt " +
            " from mbr_info mb inner join mbr_assets ma on mb.id = ma.mbr_id left join mbr_recharge_record mrr on ma.mbr_id = mrr.mbr_id and mrr.status = 1 left join mbr_withdraw_record mwr on ma.mbr_id = mwr.mbr_id and mwr.status = 1 left join mbr_info pmb on mb.parent_id = pmb.id " +
            "left join (select login_ip,count(1) as cnt from mbr_info group by login_ip) login_stat on mb.login_ip = login_stat.login_ip " +
            "left join (select register_ip,count(1) as cnt from mbr_info group by register_ip) reg_stat on mb.register_ip = reg_stat.register_ip ${ew.customSqlSegment}")
    IPage<MbrInfo> getPageByEw(@Param("page") IPage<MbrInfo> page, @Param("ew") QueryWrapper<Object> ew);


    @Update("update mbr_info set status = #{status} where id = #{id} and merchant_id = #{merchantId}")
    void updMbrStatus(@Param("id") Integer id, @Param("status") MbrStatus status,@Param("merchantId")Integer merchantId);

    @Select("select * from mbr_info where merchant_id = #{merchantId} and id = #{id} and disabled = false")
    MbrInfo getByMerchantIdAndId(@Param("merchantId")Integer merchantId,@Param("id")Integer id);

    @Update("update mbr_info set status = #{status} where relationship_route like concat(#{relationshipRoute},',%') and merchant_id = #{merchantId}")
    void updMbrStatusByRelationshipAndMerchantId(@Param("relationshipRoute")String relationshipRoute,@Param("status")MbrStatus status,@Param("merchantId")Integer merchantId);
}
