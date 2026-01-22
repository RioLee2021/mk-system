package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.pojo.PermMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 9:55
 */
@Mapper
public interface PermMenuMapper extends BaseMapper<PermMenu> {

    @Select("select * from perm_menu where perm_menu_group = #{group} and menu_path = #{path}")
    PermMenu getByGroupAndPath(@Param("group") PermMenuGroup group, @Param("path") String path);

    @Select("select pm.* from auth_menu_config amc inner join perm_menu pm on amc.menu_id = pm.id where amc.role_type = #{roleType} and uri_flag = false order by pm.perm_menu_group,pm.sort_no")
    List<PermMenu> getMenuByRoleType(@Param("roleType") RoleType roleType);

    @Select("select * from perm_menu where menu_scope in (0,1) and uri_flag = false")
    List<PermMenu> getMenusBySuperAdmin();

    @Select("select * from perm_menu where menu_scope in (0,2) and uri_flag = false")
    List<PermMenu> getMenusByWebMaster();

    @Select("select * from perm_menu where menu_scope in (0,1)")
    List<PermMenu> getAuthBySuperAdmin();

    @Select("select * from perm_menu where menu_scope in (0,2)")
    List<PermMenu> getAuthByWebMaster();

    @Select("select pm.* from perm_menu pm where (menu_scope = 0 or menu_scope = #{scope}) and uri_flag = false order by perm_menu_group,sort_no")
    List<PermMenu> getMenuByMenuScope(@Param("scope") MenuScope scope);

    @Select("select pm.* from perm_menu pm where (menu_scope = 0 or menu_scope = #{scope}) and uri_flag = true order by perm_menu_group,sort_no")
    List<PermMenu> getUriMenuByMenuScope(@Param("scope") MenuScope scope);
}
