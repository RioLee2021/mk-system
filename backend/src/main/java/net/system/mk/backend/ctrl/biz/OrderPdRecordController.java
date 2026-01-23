package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.biz.vo.OrderPdRecordPagerRequest;
import net.system.mk.backend.serv.OrderPdRecordService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.OrderPdRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:25
 */
@RestController
@RequestMapping("/order-pd")
@Api(tags = "回购管理")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class OrderPdRecordController {

    @Resource
    private OrderPdRecordService service;

    @ApiOperation(value = "回购记录列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<OrderPdRecord> list(@Valid @RequestBody OrderPdRecordPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/confirm.do")
    @ApiOperation(value = "确认回购")
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
