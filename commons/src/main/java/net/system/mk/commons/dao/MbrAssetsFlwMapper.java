package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.pojo.MbrAssetsFlw;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:27
 */
@Mapper
public interface MbrAssetsFlwMapper extends BaseMapper<MbrAssetsFlw> {

    @Select("select maf.* from mbr_assets_flw maf inner join mbr_info mb on maf.mbr_id = mb.id ${ew.customSqlSegment}")
    IPage<MbrAssetsFlw> getPageByEw(@Param("page") IPage<MbrAssetsFlw> page, @Param("ew") QueryWrapper<Object> ew);

    @Select("select ifnull(sum(amount),0) from mbr_assets_flw where mbr_id = #{mbrId} and type in (5,7)")
    BigDecimal sumAmountByMbrId(@Param("mbrId") Integer mbrId);

    @Select("select ifnull(sum(amount),0) from mbr_assets_flw where mbr_id = #{mbrId} and type in (5,7) and create_at >= #{startTime}")
    BigDecimal sumAmountByMbrIdAndStartTime(@Param("mbrId") Integer mbrId, @Param("startTime") LocalDateTime startTime);
}
