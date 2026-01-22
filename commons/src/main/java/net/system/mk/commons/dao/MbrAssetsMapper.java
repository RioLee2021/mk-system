package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.MbrAssets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:27
 */
@Mapper
public interface MbrAssetsMapper extends BaseMapper<MbrAssets> {

    @Select("select * from mbr_assets where mbr_id = #{mbrId} for update")
    MbrAssets getByMbrIdForUpdate(@Param("mbrId") Integer mbrId);
}
