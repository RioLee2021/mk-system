package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.biz.vo.MbrAssetsFlwPagerRequest;
import net.system.mk.backend.serv.MbrAssetsFlwService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.MerchantInject;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.MbrAssetsFlw;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:36
 */
@RestController
@RequestMapping("/mbr-asset-flw")
@Api(tags = "会员账变记录")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class MbrAssetFlwController {

    @Resource
    private MbrAssetsFlwService service;

    @ApiOperation(value = "流水列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public PagerResult<MbrAssetsFlw> list(@Valid @RequestBody MbrAssetsFlwPagerRequest request) {
        return service.list(request);
    }

}
