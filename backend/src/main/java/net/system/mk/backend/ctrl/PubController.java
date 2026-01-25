package net.system.mk.backend.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.vo.CurrentTaskCnt;
import net.system.mk.backend.ctrl.vo.PermMenuTree;
import net.system.mk.backend.ctrl.vo.PermUserLoginRequest;
import net.system.mk.backend.serv.PubService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.meta.DictItem;
import net.system.mk.commons.meta.DictResponse;
import net.system.mk.commons.meta.OptionsTypeRequest;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.PermUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 9:48
 */
@RestController
@Api(tags = "公共接口")
@RequestMapping("/pub")
public class PubController {

    @Resource
    private PubService service;

    @PostMapping("/upload.do")
    @ApiOperation(value = "上传图片")
    @AuthCheck
    public ResultBody<String> upload(@RequestParam("file") MultipartFile file) {
        return service.upload(file);
    }

    @PostMapping("/currentTaskCnt.do")
    @ApiOperation(value = "当前消息数量")
    @AuthCheck
    public ResultBody<CurrentTaskCnt> currentTaskCnt() {
        return service.currentTaskCnt();
    }

    @PostMapping("/login.do")
    @ApiOperation(value = "后台账号登录")
    public ResultBody<PermUser> login(@Valid @RequestBody PermUserLoginRequest request) {
        return service.login(request);
    }

    @PostMapping("/logout.do")
    @ApiOperation(value = "后台账号登出")
    @AuthCheck
    public ResultBody<Void> logout() {
        return service.logout();
    }

    @PostMapping("/info.do")
    @ApiOperation(value = "获取后台账号信息")
    @AuthCheck
    public ResultBody<PermUser> info() {
        return service.info();
    }

    @PostMapping("/menus.do")
    @ApiOperation(value = "获取菜单")
    @AuthCheck
    public List<PermMenuTree> menus() {
        return service.menus();
    }

    @PostMapping("/dict.do")
    @ApiOperation(value = "枚举字典")
    @AuthCheck
    public List<DictResponse> dict() {
        return service.dict();
    }

    @PostMapping("/options.do")
    @ApiOperation(value = "获取下拉选项")
    @AuthCheck
    public List<DictItem> options(@Valid @RequestBody OptionsTypeRequest request) {
        return service.options(request);
    }

    @PostMapping("/zoneIdList.do")
    @ApiOperation(value = "可用时区")
    @AuthCheck
    public Set<String> zoneIdList() {
        return ZoneId.getAvailableZoneIds();
    }
}
