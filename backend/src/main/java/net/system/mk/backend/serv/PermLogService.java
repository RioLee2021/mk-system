package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.log.vo.PermUserLoginLogPagerRequest;
import net.system.mk.backend.ctrl.log.vo.PermUserOperationLogPagerRequest;
import net.system.mk.commons.dao.PermUserLoginLogMapper;
import net.system.mk.commons.dao.PermUserOperationLogMapper;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.PermUserLoginLog;
import net.system.mk.commons.pojo.PermUserOperationLog;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 10:32
 */
@Service
public class PermLogService {

    @Resource
    private PermUserLoginLogMapper permUserLoginLogMapper;
    @Resource
    private PermUserOperationLogMapper permUserOperationLogMapper;


    public PagerResult<PermUserLoginLog> listLoginLog(PermUserLoginLogPagerRequest request) {
        return PagerResult.of(permUserLoginLogMapper.getPageByEw(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    public PagerResult<PermUserOperationLog> listOperationLog(PermUserOperationLogPagerRequest request) {
        return PagerResult.of(permUserOperationLogMapper.getPageByEw(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

}
