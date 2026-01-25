package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.front.ctrl.vo.ProductInfoPagerRequest;
import net.system.mk.front.serv.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:54
 */
@RestController
@Api(tags = "产品")
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService service;


    @ApiOperation(value = "产品列表")
    @PostMapping("/list.do")
    @AuthCheck
    public PagerResult<ProductInfo> list(@Valid @RequestBody ProductInfoPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/buy.do")
    @ApiOperation(value = "购买产品")
    @AuthCheck
    public ResultBody<Void> buy(@Valid @RequestBody BaseUpdateRequest request) {
        return service.buy(request);
    }
}
