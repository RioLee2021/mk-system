package net.system.mk.backend.ctrl.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.basic.vo.ProductInfoAddRequest;
import net.system.mk.backend.ctrl.basic.vo.ProductInfoPagerRequest;
import net.system.mk.backend.ctrl.system.vo.BatchIdsRequest;
import net.system.mk.backend.serv.ProductInfoService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.ProductInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 18:00
 */
@RestController
@RequestMapping("/product")
@Api(tags = "产品管理")
@PermMenuScan(group = PermMenuGroup.basic_setting, scope = MenuScope.merchant)
public class ProductInfoController {

    @Resource
    private ProductInfoService service;

    @ApiOperation(value = "产品列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<ProductInfo> list(@Valid @RequestBody ProductInfoPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/brandOps.do")
    @ApiOperation(value = "商户ID下拉")
    @AuthCheck
    @MerchantOnly
    public List<DictItem> brandOps() {
        return service.brandOps();
    }

    @PostMapping("/add.do")
    @ApiOperation(value = "添加产品")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> add(@Valid @RequestBody ProductInfoAddRequest request) {
        return service.add(request);
    }

    @PostMapping("/batUpdSpecialOffer.do")
    @ApiOperation(value = "批量设置特价标识")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> batUpdSpecialOffer(@Valid @RequestBody BatchIdsRequest request) {
        return service.batUpdSpecialOffer(request);
    }

    @PostMapping("/delete.do")
    @ApiOperation(value = "删除产品")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> delete(@Valid @RequestBody BaseUpdateRequest request) {
        return service.delete(request);
    }

    @PostMapping("/importProduct.do")
    @ApiOperation(value = "导入产品")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> importProduct(@RequestParam("file")MultipartFile file,@RequestParam("brandId")Integer brandId) {
        return service.importProduct(file,brandId);
    }

    @GetMapping("/downloadTemp.do")
    @ApiOperation(value = "下载模板")
    @AuthCheck
    @MerchantOnly
    public void downloadTemp(HttpServletResponse response) {
        service.downloadTemp(response);
    }


}
