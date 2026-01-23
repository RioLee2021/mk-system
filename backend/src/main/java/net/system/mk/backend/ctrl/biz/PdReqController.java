package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import net.system.mk.backend.ctrl.biz.vo.DistributionPdRequest;
import net.system.mk.backend.ctrl.biz.vo.MbrPdRequestPagerRequest;
import net.system.mk.backend.serv.PdReqService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrPdRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 17:44
 */
@RestController
@RequestMapping("/req-pd")
@Api(tags = "分配订单")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class PdReqController {

    @Resource
    private PdReqService service;

    @ApiOperation(value = "申请列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<MbrPdRequest> list(@Valid @RequestBody MbrPdRequestPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/productOps.do")
    @ApiOperation(value = "商品列表下拉")
    @AuthCheck
    @MerchantOnly
    public List<DictItem> productOps() {
        return service.productOps();
    }

    @PostMapping("/distribution.do")
    @ApiOperation(value = "分配")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> distribution(@Valid @RequestBody DistributionPdRequest request) {
        return service.distribution(request);
    }
}
