package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.front.ctrl.vo.MbeRegisterRequest;
import net.system.mk.front.ctrl.vo.MbrLoginRequest;
import net.system.mk.front.ctx.MemberContext;
import net.system.mk.front.serv.PubService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 9:23
 */
@RestController
@Api(tags = "公共接口")
@RequestMapping("/pub")
public class PubController {

    @Resource
    private PubService service;

    @PostMapping("/register.do")
    @ApiOperation(value = "注册")
    public ResultBody<String> register(@Valid @RequestBody MbeRegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login.do")
    @ApiOperation(value = "登录")
    public ResultBody<String> login(@Valid @RequestBody MbrLoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/logout.do")
    @ApiOperation(value = "登出")
    @AuthCheck(ctxScope = CtxScope.wap)
    public ResultBody<Void> logout() {
        return service.logout();
    }

    @PostMapping("/info.do")
    @ApiOperation(value = "用户信息")
    @AuthCheck(ctxScope = CtxScope.wap)
    public ResultBody<MemberContext> info() {
        return service.info();
    }

    @PostMapping("/upload.do")
    @ApiOperation(value = "上传图片")
    @AuthCheck(ctxScope = CtxScope.wap)
    public ResultBody<String> upload(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }
}
