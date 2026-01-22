package net.system.mk.commons.serv;

import cn.hutool.core.util.IdUtil;
import net.system.mk.commons.dao.MbrAssetsFlwMapper;
import net.system.mk.commons.dao.MbrAssetsMapper;
import net.system.mk.commons.enums.AssetsFlwType;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.pojo.MbrAssets;
import net.system.mk.commons.pojo.MbrAssetsFlw;
import net.system.mk.commons.redis.lock.DistributedLock;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 14:42
 */
@Component
public class MbrAssetHelper {

    @Resource
    private MbrAssetsMapper mbrAssetsMapper;

    @Resource
    private MbrAssetsFlwMapper mbrAssetsFlwMapper;

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    @DistributedLock(keyPrefix = "mbrAssetHelper:submitAssetChange:", key = "#mbrId")
    public String submitAssetChange(Integer mbrId, BigDecimal amount, AssetsFlwType type,String remark,Object relatedNo,Integer relatedId){
        BigDecimal amt = amount.abs();
        MbrAssets mba = mbrAssetsMapper.getByMbrIdForUpdate(mbrId);
        if (mba==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "资产不存在");
        }
        MbrAssetsFlw mbf = new MbrAssetsFlw();
        mbf.setFlwNo(type.name().substring(0,3)+ IdUtil.getSnowflakeNextIdStr());
        mbf.setMbrId(mbrId).setAmount(amt).setType(type).setRemark(remark).setRelatedNo(String.valueOf(relatedNo)).setRelatedId(relatedId)
                .setBeforeAmt(mba.getBalance());
        switch (type){
            case system_add:
            case recharge_add:
            case commission_add:
            case repurchase_commission_add:
            case repurchase_add:
            case withdraw_rollback_add:
                mba.setBalance(mba.getBalance().add(amt));
                mbf.setAfterAmt(mba.getBalance());
                break;
            case withdraw_reduce:
            case system_reduce:
            case order_payment_reduce:
                mba.setBalance(mba.getBalance().subtract(amt));
                mbf.setAfterAmt(mba.getBalance());
                break;
            case integral_add:
                mbf.setBeforeAmt(BigDecimal.valueOf(mba.getIntegral()));
                mba.setIntegral(mba.getIntegral()+amt.intValue());
                mbf.setAfterAmt(BigDecimal.valueOf(mba.getIntegral()));
                break;
            case integral_reduce:
                mbf.setBeforeAmt(BigDecimal.valueOf(mba.getIntegral()));
                mba.setIntegral(mba.getIntegral()-amt.intValue());
                mbf.setAfterAmt(BigDecimal.valueOf(mba.getIntegral()));
                break;
        }
        mbrAssetsMapper.updateById(mba);
        mbrAssetsFlwMapper.insert(mbf);
        return mbf.getFlwNo();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    @DistributedLock(keyPrefix = "mbrAssetHelper:submitAssetChange:", key = "#mbrId")
    public String submitAssetChange(Integer mbrId, BigDecimal amount, AssetsFlwType type,String remark,Object relatedNo){
        return submitAssetChange(mbrId, amount, type, remark, relatedNo, null);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    @DistributedLock(keyPrefix = "mbrAssetHelper:submitAssetChange:", key = "#mbrId")
    public String submitAssetChange(Integer mbrId, BigDecimal amount, AssetsFlwType type,String remark,int relatedId){
        return submitAssetChange(mbrId, amount, type, remark, null, relatedId);
    }
}
