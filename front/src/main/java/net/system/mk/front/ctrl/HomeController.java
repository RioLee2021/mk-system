package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.meta.LangTypeOnlyRequest;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.NoticeRecord;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.front.ctrl.vo.HomeDataResponse;
import net.system.mk.front.serv.HomeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static net.system.mk.commons.enums.CtxScope.wap;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:53
 */
@RestController
@Api(tags = "首页")
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService service;

    @PostMapping("/notice.do")
    @ApiOperation(value = "网站公告")
    @AuthCheck(ctxScope = wap)
    public List<NoticeRecord> notice(@Valid @RequestBody LangTypeOnlyRequest request) {
        return service.notice(request);
    }

    @PostMapping("/data.do")
    @ApiOperation(value = "首页数据")
    @AuthCheck(ctxScope = wap)
    public ResultBody<HomeDataResponse> data() {
        return service.data();
    }

    @PostMapping("/grabbing.do")
    @ApiOperation(value = "系统抢单")
    @AuthCheck(ctxScope = wap)
    public ResultBody<ProductInfo> grabbing() {
        return service.grabbing();
    }
    
}
