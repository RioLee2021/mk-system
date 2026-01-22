package net.system.mk.backend.serv;

import com.google.common.collect.Lists;
import net.system.mk.backend.ctrl.auth.vo.AuthMenuConfigPagerRequest;
import net.system.mk.backend.ctrl.auth.vo.AuthMenuSaveRequest;
import net.system.mk.backend.ctrl.auth.vo.RoleTypeRequest;
import net.system.mk.backend.ctrl.vo.PermMenuTree;
import net.system.mk.commons.dao.AuthMenuConfigMapper;
import net.system.mk.commons.dao.PermMenuMapper;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.RoleType;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.AuthMenuConfig;
import net.system.mk.commons.pojo.PermMenu;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:01
 */
@Service
public class AuthMenuService {

    @Resource
    private AuthMenuConfigMapper authMenuConfigMapper;
    @Resource
    private PermMenuMapper permMenuMapper;


    public PagerResult<AuthMenuConfig> list(AuthMenuConfigPagerRequest request) {
        return PagerResult.of(authMenuConfigMapper.getPageByEw(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    public List<PermMenuTree> menuTree(RoleTypeRequest request) {
        MenuScope scope = request.getRoleType() == RoleType.SuperAdmin ? MenuScope.platform : MenuScope.merchant;
        //菜单
        List<PermMenu> menus = permMenuMapper.getMenuByMenuScope(scope);
        //URI菜单
        List<PermMenu> uris = permMenuMapper.getUriMenuByMenuScope(scope);
        //已选ID
        List<Integer> selectedIds = authMenuConfigMapper.getMenuIdByRoleType(request.getRoleType());
        List<PermMenuTree> trees = menus.stream().map(PermMenuTree::of).collect(Collectors.toList());
        for (PermMenuTree tree : trees) {
            tree.setSelected(selectedIds.contains(tree.getId()));
        }
        List<PermMenuTree> uriTrees = uris.stream().map(PermMenuTree::of).collect(Collectors.toList());
        for (PermMenuTree tree : uriTrees) {
            tree.setSelected(selectedIds.contains(tree.getId()));
            String path = tree.getMenuPath();
            for (PermMenuTree menuTree : trees) {
                String menuPath = menuTree.getMenuPath();
                String left = path.replaceFirst(menuPath, "");
                if (path.startsWith(menuPath) && left.startsWith("/")) {
                    menuTree.getChildren().add(tree);
                }
            }
        }
        return trees;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(AuthMenuSaveRequest request) {
        MenuScope scope = request.getRoleType() == RoleType.SuperAdmin ? MenuScope.platform : MenuScope.merchant;
        authMenuConfigMapper.delByRoleType(request.getRoleType());
        List<String> names = Lists.newArrayList();
        //特殊处理站点和超管
        if (request.getRoleType()==RoleType.SuperAdmin){
            request.setMenuIds(permMenuMapper.getAuthBySuperAdmin().stream().map(PermMenu::getId).collect(Collectors.toList()));
        }
        if (request.getRoleType()==RoleType.WebMaster){
            request.setMenuIds(permMenuMapper.getAuthByWebMaster().stream().map(PermMenu::getId).collect(Collectors.toList()));
        }
        for (Integer menuId : request.getMenuIds()) {
            PermMenu menu = permMenuMapper.selectById(menuId);
            if (menu==null){
                continue;
            }
            if (menu.getMenuScope()!=scope&&menu.getMenuScope()!=MenuScope.both){
                continue;
            }
            AuthMenuConfig config = new AuthMenuConfig();
            config.setRoleType(request.getRoleType());
            config.setMenuId(menuId);
            config.setCreateBy(request.getOperator().account());
            authMenuConfigMapper.insert(config);
            names.add(menu.getMenuName()+"-"+(menu.getUriFlag()?"URI":"菜单"));
        }
        request.setAllMemo("添加菜单："+String.join(",",names));
    }
}
