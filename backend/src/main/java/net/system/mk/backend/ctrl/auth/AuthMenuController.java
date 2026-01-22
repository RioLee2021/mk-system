package net.system.mk.backend.ctrl.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.auth.vo.AuthMenuSaveRequest;
import net.system.mk.backend.ctrl.auth.vo.RoleTypeRequest;
import net.system.mk.backend.ctrl.vo.PermMenuTree;
import net.system.mk.backend.serv.AuthMenuService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 8:59
 */
@RestController
@RequestMapping("/auth-menu")
@Api(tags = "权限菜单设置")
@PermMenuScan(group = PermMenuGroup.auth, scope = MenuScope.platform)
public class AuthMenuController {

    @Resource
    private AuthMenuService service;

    @PostMapping("/menuTree.do")
    @ApiOperation(value = "获取菜单树")
    @AuthCheck
    @PlatformOnly
    public List<PermMenuTree> menuTree(@Valid @RequestBody RoleTypeRequest request) {
        return service.menuTree(request);
    }

    @PostMapping("/save.do")
    @ApiOperation(value = "保存权限菜单")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public void save(@Valid @RequestBody AuthMenuSaveRequest request) {
        service.save(request);
    }
}
