package net.system.mk.backend.ctrl.operator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.operator.vo.MbrWithdrawRecordPagerRequest;
import net.system.mk.backend.serv.MbrWithdrawService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrWithdrawRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:08
 */
@RestController
@RequestMapping("/mbr-withdraw")
@Api(tags = "提现记录")
@PermMenuScan(group = PermMenuGroup.operator_manage, scope = MenuScope.merchant)
public class MbrWithdrawController {

    @Resource
    private MbrWithdrawService service;

    @ApiOperation(value = "提现列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<MbrWithdrawRecord> list(@Valid @RequestBody MbrWithdrawRecordPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/confirm.do")
    @ApiOperation(value = "通过")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> confirm(@Valid @RequestBody BaseUpdateRequest request) {
        return service.confirm(request);
    }

    @PostMapping("/reject.do")
    @ApiOperation(value = "拒绝")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> reject(@Valid @RequestBody BaseUpdateRequest request) {
        return service.reject(request);
    }

}
