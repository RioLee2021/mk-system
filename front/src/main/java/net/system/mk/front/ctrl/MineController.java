package net.system.mk.front.ctrl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.system.mk.commons.anno.AuthCheck;
import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.meta.*;
import net.system.mk.commons.pojo.ChatMsgLog;
import net.system.mk.commons.pojo.MbrAssetsFlw;
import net.system.mk.commons.pojo.MbrRechargeRecord;
import net.system.mk.commons.pojo.MbrWithdrawRecord;
import net.system.mk.front.ctrl.vo.AvatarUpdRequest;
import net.system.mk.front.ctrl.vo.BankInfoSaveRequest;
import net.system.mk.front.ctrl.vo.MsgContentRequest;
import net.system.mk.front.serv.MineService;
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
 * @date 2026-01-2026/1/24/0024 11:56
 */
@RestController
@Api(tags = "我的")
@RequestMapping("/mine")
public class MineController {

    @Resource
    private MineService service;

    @PostMapping("/rechargeRecord.do")
    @ApiOperation(value = "充值记录")
    @AuthCheck(ctxScope = wap)
    public PagerResult<MbrRechargeRecord> rechargeRecord(@Valid @RequestBody PagerRequest request) {
        return service.rechargeRecord(request);
    }

    @PostMapping("/withdrawRecord.do")
    @ApiOperation(value = "提现记录")
    @AuthCheck(ctxScope = wap)
    public PagerResult<MbrWithdrawRecord> withdrawRecord(@Valid @RequestBody PagerRequest request) {
        return service.withdrawRecord(request);
    }

    @PostMapping("/fundDetails.do")
    @ApiOperation(value = "资金明细")
    @AuthCheck(ctxScope = wap)
    public PagerResult<MbrAssetsFlw> fundDetails(@Valid @RequestBody PagerRequest request) {
        return service.fundDetails(request);
    }

    @PostMapping("/awaitPayOrder.do")
    @ApiOperation(value = "待付款订单")
    @AuthCheck(ctxScope = wap)
    public PagerResult<ProductOrderResponse> awaitPayOrder(@Valid @RequestBody PagerRequest request) {
        return service.awaitPayOrder(request);
    }

    @PostMapping("/completedOrder.do")
    @ApiOperation(value = "已完成订单")
    @AuthCheck(ctxScope = wap)
    public PagerResult<ProductOrderResponse> completedOrder(@Valid @RequestBody PagerRequest request) {
        return service.completedOrder(request);
    }

    @PostMapping("/repurchaseSubmit.do")
    @ApiOperation(value = "申请回购")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> repurchaseSubmit(@Valid @RequestBody BaseUpdateRequest request) {
        return service.repurchaseSubmit(request);
    }


    @PostMapping("/repurchaseOrder.do")
    @ApiOperation(value = "待回购订单")
    @AuthCheck(ctxScope = wap)
    public PagerResult<ProductOrderResponse> repurchaseOrder(@Valid @RequestBody PagerRequest request) {
        return service.repurchaseOrder(request);
    }



    @PostMapping("/repurchasedOrder.do")
    @ApiOperation(value = "已回购订单")
    @AuthCheck(ctxScope = wap)
    public PagerResult<ProductOrderResponse> repurchasedOrder(@Valid @RequestBody PagerRequest request) {
        return service.repurchasedOrder(request);
    }

    @PostMapping("/saveBankInfo.do")
    @ApiOperation(value = "修改银行卡信息")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> saveBankInfo(@Valid @RequestBody BankInfoSaveRequest request) {
        return service.saveBankInfo(request);
    }

    @PostMapping("/modifyLoginPassword.do")
    @ApiOperation(value = "修改登录密码")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> modifyLoginPassword(@Valid @RequestBody PasswordUpdRequest request) {
        return service.modifyLoginPassword(request);
    }

    @PostMapping("/modifyWithdrawPassword.do")
    @ApiOperation(value = "修改提现密码")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> modifyWithdrawPassword(@Valid @RequestBody PasswordUpdRequest request) {
        return service.modifyWithdrawPassword(request);
    }

    @PostMapping("/modifyAvatar.do")
    @ApiOperation(value = "修改头像")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> modifyAvatar(@Valid @RequestBody AvatarUpdRequest request) {
        return service.modifyAvatar(request);
    }

    @PostMapping("/getMessage.do")
    @ApiOperation(value = "获取聊天记录")
    @AuthCheck(ctxScope = wap)
    public List<ChatMsgLog> getMessage(@Valid @RequestBody BaseUpdateRequest request) {
        return service.getMessage(request);
    }

    @PostMapping("/sendMessage.do")
    @ApiOperation(value = "发送消息")
    @AuthCheck(ctxScope = wap)
    public ResultBody<Void> sendMessage(@Valid @RequestBody MsgContentRequest request) {
        return service.sendMessage(request);
    }
}
