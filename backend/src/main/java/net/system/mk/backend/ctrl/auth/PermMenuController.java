package net.system.mk.backend.ctrl.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.auth.vo.PermMenuPagerRequest;
import net.system.mk.backend.ctrl.auth.vo.PermMenuUpdateRequest;
import net.system.mk.backend.serv.PermMenuService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.PermMenu;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 20:59
 */
@RestController
@RequestMapping("/perm-menu")
@Api(tags = "菜单管理")
@PermMenuScan(group = PermMenuGroup.auth, scope = MenuScope.platform)
public class PermMenuController {

    @Resource
    private PermMenuService service;

    @ApiOperation(value = "菜单列表")
    @PostMapping("/list.do")
    @AuthCheck
    @PlatformOnly
    public PagerResult<PermMenu> list(@Valid @RequestBody PermMenuPagerRequest request) {
        return service.list(request);
    }


    @ApiOperation(value = "更新菜单")
    @PostMapping("/update.do")
    @AuthCheck
    @PlatformOnly
    public ResultBody<Void> update(@Valid @RequestBody PermMenuUpdateRequest request) {
        service.update(request);
        return ResultBody.success();
    }

}
