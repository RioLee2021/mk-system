package net.system.mk.commons.ctx;

import net.system.mk.commons.dao.MerchantConfigMapper;
import net.system.mk.commons.pojo.MerchantConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2025-12-2025/12/5/0005 11:05
 */
@Component
public class GlobalCache {

    @Resource
    private MerchantConfigMapper merchantConfigMapper;


    @Cacheable(cacheNames = "merchant_config",key = "#mchId",unless = "#result==null")
    public MerchantConfig getMerchantConfig(Integer mchId) {
        return merchantConfigMapper.selectById(mchId);
    }

    @CacheEvict(cacheNames = "merchant_config")
    public void clearMerchantConfigCache(){}

}
