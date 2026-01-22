package net.system.mk.commons.serv;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.dao.AuthMenuConfigMapper;
import net.system.mk.commons.dao.PermMenuMapper;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.pojo.AuthMenuConfig;
import net.system.mk.commons.pojo.PermMenu;
import net.system.mk.commons.utils.SpringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 8:28
 */
@Component
@Slf4j
public class InitializationUtils {

    @Resource
    private AppConfig config;

    @Resource
    private PermMenuMapper permMenuMapper;

    @Resource
    private AuthMenuConfigMapper authMenuConfigMapper;

    public void loadZoneOffset() {
        if (config.getZoneOffset() != null) {
            TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.ofTotalSeconds(config.getZoneOffset())));
            log.info("时区已设置为：{}", TimeZone.getDefault().getDisplayName());
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void loadPermMenu() {
        Map<String, Object> ctrMap = SpringUtils.getBeanWithAnnotation(PermMenuScan.class);
        List<PermMenu> current = Lists.newArrayList();
        List<PermMenu> db = permMenuMapper.selectList(null);
        for (Object ctr : ctrMap.values()) {
            PermMenuScan pms = AnnotationUtils.findAnnotation(ctr.getClass(), PermMenuScan.class);
            if (pms != null) {
                Api api = AnnotationUtils.findAnnotation(ctr.getClass(), Api.class);
                RequestMapping classRequestMapping = AnnotationUtils.findAnnotation(ctr.getClass(), RequestMapping.class);
                if (api != null && classRequestMapping != null) {
                    //构建菜单
                    PermMenu menu = new PermMenu();
                    menu.setPermMenuGroup(pms.group()).setMenuName(api.tags()[0]).setMenuPath(classRequestMapping.value()[0])
                            .setIcon(pms.group().getIcon()).setSortNo(0).setMenuScope(pms.scope()).setUriFlag(Boolean.FALSE);
                    //查询菜单是否存在
                    PermMenu old = permMenuMapper.getByGroupAndPath(menu.getPermMenuGroup(), menu.getMenuPath());
                    if (old == null) {
                        permMenuMapper.insert(menu);
                        log.warn("菜单组：{},已添加菜单：{}", menu.getPermMenuGroup().getChName(), menu.getMenuName());
                        //为站点和直管添加权限
                        AuthMenuConfig root = new AuthMenuConfig();
                        root.setCreateBy("INIT");
                        root.setRoleType(RoleType.SuperAdmin).setMenuId(menu.getId());
                        AuthMenuConfig master = new AuthMenuConfig();
                        master.setCreateBy("INIT");
                        master.setRoleType(RoleType.WebMaster).setMenuId(menu.getId());
                        switch (menu.getMenuScope()){
                            case both:
                                authMenuConfigMapper.insert(root);
                                authMenuConfigMapper.insert(master);
                                break;
                            case platform:
                                authMenuConfigMapper.insert(root);
                                break;
                            case merchant:
                                authMenuConfigMapper.insert(master);
                                break;
                        }
                    } else {
                        menu.setId(old.getId());
                        //不改变数据库中排序
                        menu.setSortNo(old.getSortNo()).setIcon(old.getIcon());
                        permMenuMapper.updateById(menu);
                    }
                    current.add(menu);
                    //构建URI
                    Method[] methods = ctr.getClass().getDeclaredMethods();
                    for (Method m : methods) {
                        String path = null;
                        PostMapping postMapping = AnnotationUtils.findAnnotation(m, PostMapping.class);
                        GetMapping getMapping = AnnotationUtils.findAnnotation(m, GetMapping.class);
                        ApiOperation apiOperation = AnnotationUtils.findAnnotation(m, ApiOperation.class);
                        if (postMapping != null) {
                            path = postMapping.value()[0];
                        }
                        if (getMapping != null) {
                            path = getMapping.value()[0];
                        }
                        if (path != null && apiOperation != null) {
                            PermMenu uri = new PermMenu();
                            uri.setPermMenuGroup(pms.group()).setMenuName(menu.getMenuName() + ":" + apiOperation.value())
                                    .setMenuPath(menu.getMenuPath() + path).setIcon(menu.getIcon()).setSortNo(0).setMenuScope(MenuScope.both)
                                    .setUriFlag(Boolean.TRUE);
                            MerchantOnly merchantOnly = AnnotationUtils.findAnnotation(m, MerchantOnly.class);
                            PlatformOnly platformOnly = AnnotationUtils.findAnnotation(m, PlatformOnly.class);
                            if (merchantOnly != null) {
                                uri.setMenuScope(MenuScope.merchant);
                            }
                            if (platformOnly != null) {
                                uri.setMenuScope(MenuScope.platform);
                            }
                            old = permMenuMapper.getByGroupAndPath(uri.getPermMenuGroup(), uri.getMenuPath());
                            if (old == null) {
                                permMenuMapper.insert(uri);
                                log.warn("已添加URI：{},PATH为：{}", uri.getMenuName(), uri.getMenuPath());
                                //为站点和直管添加权限
                                AuthMenuConfig root = new AuthMenuConfig();
                                root.setCreateBy("INIT");
                                root.setRoleType(RoleType.SuperAdmin).setMenuId(uri.getId());
                                AuthMenuConfig master = new AuthMenuConfig();
                                master.setCreateBy("INIT");
                                master.setRoleType(RoleType.WebMaster).setMenuId(uri.getId());
                                switch (uri.getMenuScope()){
                                    case both:
                                        authMenuConfigMapper.insert(root);
                                        authMenuConfigMapper.insert(master);
                                        break;
                                    case platform:
                                        authMenuConfigMapper.insert(root);
                                        break;
                                    case merchant:
                                        authMenuConfigMapper.insert(master);
                                        break;
                                }
                            } else {
                                uri.setId(old.getId());
                                permMenuMapper.updateById(uri);
                            }
                            current.add(uri);
                        }
                    }
                }
            }
        }
        //比较数据库中是否有多余的菜单
        for (PermMenu dbMenu : db) {
            boolean exist = false;
            for (PermMenu menu : current) {
                if (dbMenu.getPermMenuGroup() == menu.getPermMenuGroup() && dbMenu.getMenuPath().equals(menu.getMenuPath())) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                permMenuMapper.deleteById(dbMenu);
                authMenuConfigMapper.delByMenuId(dbMenu.getId());
                String fmt = dbMenu.getUriFlag() ? "已删除URI：{}" : "已删除菜单：{}";
                log.warn(fmt, dbMenu.getMenuName());
            }
        }
    }
}
