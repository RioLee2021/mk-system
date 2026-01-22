package net.system.mk.backend.serv;

import cn.hutool.core.bean.BeanUtil;
import net.system.mk.backend.ctrl.basic.vo.BrandInfoSaveRequest;
import net.system.mk.commons.dao.BrandInfoMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.BrandInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 17:47
 */
@Service
public class BrandInfoService {

    @Resource
    private BrandInfoMapper brandInfoMapper;

    public PagerResult<BrandInfo> list(PagerRequest request) {
        return PagerResult.of(brandInfoMapper.selectPage(request.toPage(),null));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void add(BrandInfoSaveRequest request) {
        BrandInfo data = new BrandInfo();
        BeanUtil.copyProperties(request,data,"id");
        brandInfoMapper.insert(data);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void update(BrandInfoSaveRequest request) {
        BrandInfo data = brandInfoMapper.selectById(request.getId());
        if (data == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "数据不存在");
        }
        BeanUtil.copyProperties(request,data,"id");
        brandInfoMapper.updateById(data);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void delete(BaseUpdateRequest request) {
        BrandInfo data = brandInfoMapper.selectById(request.getId());
        if (data == null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "数据不存在");
        }
        data.setDisabled(Boolean.TRUE);
        brandInfoMapper.updateById(data);
    }
}
