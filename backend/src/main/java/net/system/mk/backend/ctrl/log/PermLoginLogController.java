package net.system.mk.backend.ctrl.log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.log.vo.PermUserLoginLogPagerRequest;
import net.system.mk.backend.serv.PermLogService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.PermUserLoginLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:30
 */
@RestController
@RequestMapping("/perm-login-log")
@Api(tags = "后台登录日志")
@PermMenuScan(group = PermMenuGroup.log, scope = MenuScope.platform)
public class PermLoginLogController {

    @Resource
    private PermLogService service;

    @ApiOperation(value = "后台登录日志列表")
    @PostMapping("/list.do")
    @AuthCheck
    @PlatformOnly
    public PagerResult<PermUserLoginLog> list(@Valid @RequestBody PermUserLoginLogPagerRequest request) {
        return service.listLoginLog(request);
    }


}
