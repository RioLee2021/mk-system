package net.system.mk.backend.ctrl.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.basic.vo.BrandInfoSaveRequest;
import net.system.mk.backend.serv.BrandInfoService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.MerchantInject;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.BrandInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static net.system.mk.commons.meta.ResultBody.success;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:46
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌管理")
@PermMenuScan(group = PermMenuGroup.basic_setting, scope = MenuScope.merchant)
public class BrandInfoController {

    @Resource
    private BrandInfoService service;

    @ApiOperation(value = "品牌列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<BrandInfo> list(@Valid @RequestBody PagerRequest request) {
        return service.list(request);
    }

    @ApiOperation(value = "新增品牌")
    @PostMapping("/add.do")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> add(@Valid @RequestBody BrandInfoSaveRequest request) {
        service.add(request);
        return success();
    }

    @ApiOperation(value = "更新品牌")
    @PostMapping("/update.do")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> update(@Valid @RequestBody BrandInfoSaveRequest request) {
        service.update(request);
        return success();
    }

    @ApiOperation(value = "删除品牌")
    @PostMapping("/delete.do")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> delete(@Valid @RequestBody BaseUpdateRequest request) {
        service.delete(request);
        return success();
    }
}
