package net.system.mk.backend.ctrl.log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.log.vo.PermUserOperationLogPagerRequest;
import net.system.mk.backend.serv.PermLogService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.PermUserOperationLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:37
 */
@RestController
@RequestMapping("/perm-operation-log")
@Api(tags = "后台操作日志")
@PermMenuScan(group = PermMenuGroup.log, scope = MenuScope.platform)
public class PermOperationLogController {

    @Resource
    private PermLogService service;

    @ApiOperation(value = "操作日志列表")
    @PostMapping("/list.do")
    @AuthCheck
    @PlatformOnly
    public PagerResult<PermUserOperationLog> list(@Valid @RequestBody PermUserOperationLogPagerRequest request) {
        return service.listOperationLog(request);
    }


}
