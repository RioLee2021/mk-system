package net.system.mk.backend.ctrl.operator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.operator.vo.MbrRechargeRecordPagerRequest;
import net.system.mk.backend.serv.MbRechargeService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.MbrRechargeRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:04
 */
@RestController
@RequestMapping("/mbr-recharge")
@Api(tags = "充值记录")
@PermMenuScan(group = PermMenuGroup.operator_manage, scope = MenuScope.merchant)
public class MbRechargeController {

    @Resource
    private MbRechargeService service;

    @ApiOperation(value = "充值列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<MbrRechargeRecord> list(@Valid @RequestBody MbrRechargeRecordPagerRequest request) {
        return service.list(request);
    }


}
