package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.pojo.PermUserLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 10:34
 */
@Mapper
public interface PermUserLoginLogMapper extends BaseMapper<PermUserLoginLog> {

    @Select("select pul.*,pu.account from perm_user_login_log pul inner join perm_user pu on pul.perm_user_id = pu.id ${ew.customSqlSegment}")
    IPage<PermUserLoginLog> getPageByEw(@Param("page") IPage<PermUserLoginLog> page, @Param("ew") QueryWrapper ew);
}
