package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.operator.vo.MbrRechargeRecordPagerRequest;
import net.system.mk.commons.dao.MbrRechargeRecordMapper;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.MbrRechargeRecord;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:05
 */
@Service
public class MbRechargeService {

    @Resource
    private MbrRechargeRecordMapper mbrRechargeRecordMapper;


    public PagerResult<MbrRechargeRecord> list(MbrRechargeRecordPagerRequest request) {
        return PagerResult.of(mbrRechargeRecordMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }
}
