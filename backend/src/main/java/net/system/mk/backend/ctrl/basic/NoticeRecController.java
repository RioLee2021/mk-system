package net.system.mk.backend.ctrl.basic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.basic.vo.NoticeRecordPagerRequest;
import net.system.mk.backend.ctrl.basic.vo.NoticeRecordSaveRequest;
import net.system.mk.backend.serv.NoticeRecordService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.MerchantInject;
import net.system.mk.commons.anno.PermOperationLogged;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.NoticeRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static net.system.mk.commons.meta.ResultBody.success;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:51
 */
@RestController
@RequestMapping("/notice")
@Api(tags = "公告管理")
@PermMenuScan(group = PermMenuGroup.basic_setting, scope = MenuScope.merchant)
public class NoticeRecController {

    @Resource
    private NoticeRecordService service;

    @ApiOperation(value = "公告列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<NoticeRecord> list(@Valid @RequestBody NoticeRecordPagerRequest request) {
        return service.list(request);
    }

    @ApiOperation(value = "新增公告")
    @PostMapping("/add.do")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> add(@Valid @RequestBody NoticeRecordSaveRequest request) {
        service.add(request);
        return success();
    }

    @ApiOperation(value = "更新公告")
    @PostMapping("/update.do")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> update(@Valid @RequestBody NoticeRecordSaveRequest request) {
        service.update(request);
        return success();
    }


}
