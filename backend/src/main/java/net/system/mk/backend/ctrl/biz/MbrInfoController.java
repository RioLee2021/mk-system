package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.biz.vo.BatchChangeMbrStatusUpdRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrCpMarkUpdRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrInfoPagerRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrInfoSaveRequest;
import net.system.mk.backend.serv.MbrInfoService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.MerchantInject;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static net.system.mk.commons.meta.ResultBody.success;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 12:25
 */
@RestController
@RequestMapping("/mbr-info")
@Api(tags = "会员信息管理")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class MbrInfoController {

    @Resource
    private MbrInfoService service;

    @ApiOperation(value = "会员列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public PagerResult<MbrInfo> list(@Valid @RequestBody MbrInfoPagerRequest request) {
        return service.list(request);
    }

    @ApiOperation(value = "新增会员")
    @PostMapping("/add.do")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    @PermOperationLogged
    public ResultBody<Void> add(@Valid @RequestBody MbrInfoSaveRequest request) {
        service.add(request);
        return success();
    }

    @PostMapping("/batChangeStatus.do")
    @ApiOperation(value = "批量修改状态")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> batChangeStatus(@Valid @RequestBody BatchChangeMbrStatusUpdRequest request) {
        return service.batChangeStatus(request);
    }

    @PostMapping("/updCpMark.do")
    @ApiOperation(value = "设置CP")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> updCpMark(@Valid @RequestBody MbrCpMarkUpdRequest request) {
        return service.updCpMark(request);
    }
}
