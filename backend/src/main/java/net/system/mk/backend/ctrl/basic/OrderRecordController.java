package net.system.mk.backend.ctrl.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.basic.vo.OrderRecordDetailRequest;
import net.system.mk.backend.ctrl.basic.vo.OrderRecordPagerRequest;
import net.system.mk.backend.serv.OrderRecordService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.ext.OrderRecordDetailResponse;
import net.system.mk.commons.ext.OrderRecordResponse;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 20:18
 */
@RestController
@RequestMapping("/order")
@Api(tags = "拼单记录")
@PermMenuScan(group = PermMenuGroup.basic_setting, scope = MenuScope.merchant)
public class OrderRecordController {

    @Resource
    private OrderRecordService service;

    @ApiOperation(value = "订单列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<OrderRecordResponse> list(@Valid @RequestBody OrderRecordPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/detail.do")
    @ApiOperation(value = "拼单详情")
    @AuthCheck
    @MerchantOnly
    public ResultBody<OrderRecordDetailResponse> detail(@Valid @RequestBody OrderRecordDetailRequest request) {
        return service.detail(request);
    }

}
