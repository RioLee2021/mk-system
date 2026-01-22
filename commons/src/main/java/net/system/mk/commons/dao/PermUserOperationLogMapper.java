package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.pojo.PermUserOperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 9:43
 */
@Mapper
public interface PermUserOperationLogMapper extends BaseMapper<PermUserOperationLog> {

    @Select("select puo.*,pu.account from perm_user_operation_log puo inner join perm_user pu on puo.perm_user_id = pu.id ${ew.customSqlSegment}")
    IPage<PermUserOperationLog> getPageByEw(@Param("page") IPage<PermUserOperationLog> page, @Param("ew") QueryWrapper<Object> ew);
}
