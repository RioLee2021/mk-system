package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.PermUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 10:33
 */
@Mapper
public interface PermUserMapper extends BaseMapper<PermUser> {

    @Select("select * from perm_user where account=#{account} and password=#{password}")
    PermUser getByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    @Select("select * from perm_user where account= #{account} limit 1")
    PermUser getByAccount(@Param("account") String account);
}
