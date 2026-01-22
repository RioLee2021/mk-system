package net.system.mk.backend.ctrl.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.system.vo.BatchIdsRequest;
import net.system.mk.backend.ctrl.system.vo.PermUserAddRequest;
import net.system.mk.backend.ctrl.system.vo.PermUserPagerRequest;
import net.system.mk.backend.serv.PermUserService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.OtpUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.PermUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static net.system.mk.commons.meta.ResultBody.success;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 9:56
 */
@RestController
@RequestMapping("/perm-user")
@Api(tags = "后台账号设置")
@PermMenuScan(group = PermMenuGroup.system, scope = MenuScope.platform)
public class PermUserController {

    @Resource
    private PermUserService service;

    @ApiOperation(value = "后台账号列表")
    @PostMapping("/list.do")
    @AuthCheck
    @PlatformOnly
    public PagerResult<PermUser> list(@Valid @RequestBody PermUserPagerRequest request) {
        return service.list(request);
    }

    @ApiOperation(value = "新增后台账号")
    @PostMapping("/add.do")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<Void> add(@Valid @RequestBody PermUserAddRequest request) {
        service.add(request);
        return success();
    }

    @PostMapping("/resetPassword.do")
    @ApiOperation(value = "重置密码")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<String> resetPassword(@Valid @RequestBody OtpUpdateRequest request) {
        return service.resetPassword(request);
    }

    @PostMapping("/resetOtp.do")
    @ApiOperation(value = "重置OTP")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<String> resetOtp(@Valid @RequestBody OtpUpdateRequest request) {
        return service.resetOtp(request);
    }

    @PostMapping("/changeStatus.do")
    @ApiOperation(value = "启/禁用")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<Void> changeStatus(@Valid @RequestBody BaseUpdateRequest request) {
        return service.changeStatus(request);
    }

    @PostMapping("/batchKick.do")
    @ApiOperation(value = "批量踢下线")
    @AuthCheck
    @PlatformOnly
    public ResultBody<Void> batchKick(@Valid @RequestBody BatchIdsRequest request) {
        return service.batchKick(request);
    }
}
