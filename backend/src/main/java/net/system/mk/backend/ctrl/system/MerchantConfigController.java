package net.system.mk.backend.ctrl.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigAddRequest;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigPagerRequest;
import net.system.mk.backend.ctrl.system.vo.MerchantConfigUpdateRequest;
import net.system.mk.backend.serv.MerchantConfigService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.anno.menu.PlatformOnly;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.OtpUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MerchantConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:46
 */
@RestController
@RequestMapping("/mch-config")
@Api(tags = "商户配置")
@PermMenuScan(group = PermMenuGroup.system, scope = MenuScope.platform)
public class MerchantConfigController {

    @Resource
    private MerchantConfigService service;

    @ApiOperation(value = "商户配置列表")
    @PostMapping("/list.do")
    @AuthCheck
    @PlatformOnly
    public PagerResult<MerchantConfig> list(@Valid @RequestBody MerchantConfigPagerRequest request) {
        return service.list(request);
    }

    @ApiOperation(value = "新增商户配置")
    @PostMapping("/add.do")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<Void> add(@Valid @RequestBody MerchantConfigAddRequest request) {
        service.add(request);
        return ResultBody.success();
    }

    @ApiOperation(value = "更新商户配置")
    @PostMapping("/update.do")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged(detail = true)
    public ResultBody<Void> update(@Valid @RequestBody MerchantConfigUpdateRequest request) {
        service.update(request);
        return ResultBody.success();
    }

    @PostMapping("/changeStatus.do")
    @ApiOperation(value = "启/禁用")
    @AuthCheck
    @PlatformOnly
    @PermOperationLogged
    public ResultBody<Void> changeStatus(@Valid @RequestBody OtpUpdateRequest request) {
        service.changeStatus(request);
        return ResultBody.success();
    }
}
