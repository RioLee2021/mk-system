package net.system.mk.backend.ctrl.operator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.backend.ctrl.operator.vo.CustomerChatResponsePagerRequest;
import net.system.mk.backend.ctrl.operator.vo.SendChatRequest;
import net.system.mk.backend.serv.CustomerChatService;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.anno.menu.MerchantOnly;
import net.system.mk.commons.anno.menu.PermMenuScan;
import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;
import net.system.mk.commons.ext.CustomerChatDetail;
import net.system.mk.commons.ext.CustomerChatResponse;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.ChatMsgLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 22:34
 */
@RestController
@RequestMapping("/customer-chat")
@Api(tags = "在线客服")
@PermMenuScan(group = PermMenuGroup.operator_manage, scope = MenuScope.merchant)
public class CustomerChatController {

    @Resource
    private CustomerChatService service;

    @ApiOperation(value = "聊天列表")
    @PostMapping("/list.do")
    @AuthCheck
    @MerchantOnly
    public PagerResult<CustomerChatResponse> list(@Valid @RequestBody CustomerChatResponsePagerRequest request) {
        return service.list(request);
    }

    @PostMapping("/detail.do")
    @ApiOperation(value = "聊天详情")
    @AuthCheck
    @MerchantOnly
    public ResultBody<CustomerChatDetail> detail(@Valid @RequestBody BaseUpdateRequest request) {
        return service.detail(request);
    }

    @PostMapping("/deleteChatLog.do")
    @ApiOperation(value = "删除聊天记录")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> deleteChatLog(@Valid @RequestBody BaseUpdateRequest request) {
        return service.deleteChatLog(request);
    }

    @PostMapping("/sendChat.do")
    @ApiOperation(value = "发送信息")
    @AuthCheck
    @MerchantOnly
    public ResultBody<Void> sendChat(@Valid @RequestBody SendChatRequest request) {
        return service.sendChat(request);
    }

    @PostMapping("/latestMsg.do")
    @ApiOperation(value = "获取最新5条信息")
    @AuthCheck
    @MerchantOnly
    public List<ChatMsgLog> latestMsg(@Valid @RequestBody BaseUpdateRequest request) {
        return service.latestMsg(request);
    }
}
