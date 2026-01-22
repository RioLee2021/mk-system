package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.pojo.AuthMenuConfig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 9:56
 */
@Mapper
public interface AuthMenuConfigMapper extends BaseMapper<AuthMenuConfig> {

    @Delete("delete from auth_menu_config where menu_id=#{menuId}")
    void delByMenuId(@Param("menuId") Integer menuId);

    @Delete("delete from auth_menu_config where role_type= #{roleType}")
    void delByRoleType(@Param("roleType") RoleType roleType);

    @Select("select amc.*,pm.perm_menu_group,pm.menu_path,pm.menu_name,pm.menu_scope,pm.uri_flag from auth_menu_config amc inner join perm_menu pm on amc.menu_id = pm.id ${ew.customSqlSegment}")
    IPage<AuthMenuConfig> getPageByEw(@Param("page") IPage<AuthMenuConfig> page, @Param("ew") QueryWrapper ew);

    @Select("select menu_id from auth_menu_config where role_type = #{roleType}")
    List<Integer> getMenuIdByRoleType(@Param("roleType") RoleType roleType);
}
