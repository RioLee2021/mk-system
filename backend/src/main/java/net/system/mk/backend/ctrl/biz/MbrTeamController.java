package net.system.mk.backend.ctrl.biz;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.biz.vo.MbrTeamPagerRequest;
import net.system.mk.backend.ctrl.biz.vo.TeamStatusUpdRequest;
import net.system.mk.backend.serv.MbrTeamService;
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
 * @date 2026-01-2026/1/22/0022 15:50
 */
@RestController
@RequestMapping("/mbr-team")
@Api(tags = "会员团队")
@PermMenuScan(group = PermMenuGroup.biz_manage, scope = MenuScope.merchant)
public class MbrTeamController {

    @Resource
    private MbrTeamService service;

    @ApiOperation(value = "会员列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public PagerResult<MbrInfo> list(@Valid @RequestBody MbrTeamPagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/updTeamStatus.do")
    @ApiOperation(value = "更新整条线状态")
    @AuthCheck
    @MerchantOnly
    @MerchantInject
    public ResultBody<Void> updTeamStatus(@Valid @RequestBody TeamStatusUpdRequest request) {
        return service.updTeamStatus(request);
    }
}
