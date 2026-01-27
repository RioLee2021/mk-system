package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.operator.vo.MbrWithdrawRecordPagerRequest;
import net.system.mk.commons.dao.MbrWithdrawRecordMapper;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.enums.RecordStatus;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrWithdrawRecord;
import net.system.mk.commons.serv.MbrAssetHelper;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:09
 */
@Service
public class MbrWithdrawService {

    @Resource
    private MbrWithdrawRecordMapper mbrWithdrawRecordMapper;
    @Resource
    private MbrAssetHelper mbrAssetHelper;

    public PagerResult<MbrWithdrawRecord> list(MbrWithdrawRecordPagerRequest request) {
        return PagerResult.of(mbrWithdrawRecordMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> confirm(BaseUpdateRequest request) {
        MbrWithdrawRecord data = mbrWithdrawRecordMapper.selectById(request.getId());
        if (data == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "记录不存在");
        }
        if (data.getStatus().ordinal()> RecordStatus.paying.ordinal()){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "记录已处理");
        }
        data.setStatus(RecordStatus.paid);
        mbrWithdrawRecordMapper.updateById(data);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> reject(BaseUpdateRequest request) {
        MbrWithdrawRecord data = mbrWithdrawRecordMapper.selectById(request.getId());
        if (data == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "记录不存在");
        }
        if (data.getStatus().ordinal()> RecordStatus.paying.ordinal()){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "记录已处理");
        }
        data.setStatus(RecordStatus.failed);
        mbrWithdrawRecordMapper.updateById(data);
        mbrAssetHelper.submitAssetChange(data.getMbrId(), data.getAmount(), AssetsFlwType.withdraw_rollback_add, "提现拒绝", data.getRecNo());
        return ResultBody.success();
    }
}
