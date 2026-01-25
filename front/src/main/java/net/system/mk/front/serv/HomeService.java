package net.system.mk.front.serv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.*;
import net.system.mk.commons.meta.LangTypeOnlyRequest;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.BrandInfo;
import net.system.mk.commons.pojo.MbrPdRequest;
import net.system.mk.commons.pojo.NoticeRecord;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.front.ctrl.vo.HomeDataResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/24/0024 11:53
 */
@Service
public class HomeService {

    @Resource
    private NoticeRecordMapper noticeRecordMapper;
    @Resource
    private ICtxHelper iCtxHelper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private BrandInfoMapper brandInfoMapper;
    @Resource
    private MbrPdRequestMapper mbrPdRequestMapper;
    @Resource
    private OrderPdRecordMapper orderPdRecordMapper;

    public List<NoticeRecord> notice(LangTypeOnlyRequest request) {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        return noticeRecordMapper.getListByMbrIdAndLangType(ctx.id(),request.getLangType());
    }

    public ResultBody<HomeDataResponse> data() {
        HomeDataResponse rs = new HomeDataResponse();
        LambdaQueryWrapper<BrandInfo> bq = Wrappers.lambdaQuery();
        LambdaQueryWrapper<ProductInfo> pq = Wrappers.lambdaQuery();
        bq.orderByAsc(BrandInfo::getBrandSort).last(" limit 8");
        pq.eq(ProductInfo::getSpecialOffer,true).orderByAsc(ProductInfo::getOrderPrice).last(" limit 20");
        rs.setBrands(brandInfoMapper.selectList(bq));
        rs.setProducts(productInfoMapper.selectList(pq));
        return ResultBody.okData(rs);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<ProductInfo> grabbing() {
        IBaseContext ctx = iCtxHelper.getWebCtx();
        ProductInfo p = orderPdRecordMapper.getRunningPdProductByMbrId(ctx.id());
        if (p == null){
            if (mbrPdRequestMapper.cntRequestingByMbrId(ctx.id())==0){
                mbrPdRequestMapper.insert(new MbrPdRequest().setMbrId(ctx.id()));
            }
        }
        return ResultBody.okData(p);
    }
}
