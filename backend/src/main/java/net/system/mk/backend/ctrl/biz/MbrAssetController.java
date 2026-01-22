package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.biz.vo.ManualAmountRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrInfoPagerRequest;
import net.system.mk.backend.ctrl.biz.vo.RechargeSubmitRequest;
import net.system.mk.backend.ctrl.biz.vo.WithdrawSubmitRequest;
import net.system.mk.backend.serv.MbrInfoService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.MerchantInject;
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

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 14:35
 */
@RestController
@RequestMapping("/mbr-asset")
@Api(tags = "会员资产管理")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class MbrAssetController {

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

    @PostMapping("/rechargeSubmit.do")
    @ApiOperation(value = "提交充值")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> rechargeSubmit(@Valid @RequestBody RechargeSubmitRequest request) {
        return service.rechargeSubmit(request);
    }

    @PostMapping("/withdrawSubmit.do")
    @ApiOperation(value = "提交提现")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> withdrawSubmit(@Valid @RequestBody WithdrawSubmitRequest request) {
        return service.withdrawSubmit(request);
    }

    @PostMapping("/manualBalance.do")
    @ApiOperation(value = "操作余额")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> manualBalance(@Valid @RequestBody ManualAmountRequest request) {
        return service.manualBalance(request);
    }

    @PostMapping("/manualIntegral.do")
    @ApiOperation(value = "操作积分")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> manualIntegral(@Valid @RequestBody ManualAmountRequest request) {
        return service.manualIntegral(request);
    }
}
