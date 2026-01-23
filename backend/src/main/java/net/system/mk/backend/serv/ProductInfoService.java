package net.system.mk.backend.serv;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import net.system.mk.backend.ctrl.basic.vo.BatOrderRecordAddRequest;
import net.system.mk.backend.ctrl.basic.vo.ProductInfoAddRequest;
import net.system.mk.backend.ctrl.basic.vo.ProductInfoPagerRequest;
import net.system.mk.backend.ctrl.system.vo.BatchIdsRequest;
import net.system.mk.commons.dao.OrderRecordMapper;
import net.system.mk.commons.dao.ProductInfoMapper;
import net.system.mk.commons.enums.OrderStatus;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ext.ProductInfoExcel;
import net.system.mk.commons.ext.listener.ProductInfoListener;
import net.system.mk.commons.meta.BaseUpdateRequest;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.OrderRecord;
import net.system.mk.commons.pojo.ProductInfo;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 18:01
 */
@Service
public class ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    public PagerResult<ProductInfo> list(ProductInfoPagerRequest request) {
        return PagerResult.of(productInfoMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> add(ProductInfoAddRequest request) {
        ProductInfo data = new ProductInfo();
        BeanUtil.copyProperties(request, data);
        productInfoMapper.insert(data);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> delete(BaseUpdateRequest request) {
        ProductInfo data = productInfoMapper.selectById(request.getId());
        if (data == null) {
            throw new GlobalException(BUSINESS_ERROR, "数据不存在");
        }
        data.setDisabled(Boolean.TRUE);
        productInfoMapper.updateById(data);
        return ResultBody.success();
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> importProduct(MultipartFile file, Integer brandId) {
        try {
            ProductInfoListener listener = new ProductInfoListener();
            EasyExcel.read(file.getInputStream(), ProductInfo.class, listener).sheet().doRead();
            for (ProductInfoExcel data : listener.getCacheDataList()) {
                ProductInfo po = data.toProductInfo();
                po.setBrandId(brandId);
                productInfoMapper.insert(po);
            }
        } catch (IOException e) {
            throw new GlobalException(BUSINESS_ERROR, e.getMessage());
        }
        return ResultBody.success();
    }

    public void downloadTemp(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("产品信息模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            // 生成空模板（只有表头）
            EasyExcel.write(response.getOutputStream(), ProductInfoExcel.class)
                    .sheet("产品信息")
                    .doWrite(new ArrayList<>());
        } catch (IOException e) {
            throw new GlobalException(BUSINESS_ERROR, "模板下载失败");
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<Void> batUpdSpecialOffer(BatchIdsRequest request) {
        for (Integer id : request.getIds()){
            ProductInfo po = productInfoMapper.selectById(id);
            if (po!=null){
                po.setSpecialOffer(!po.getSpecialOffer());
                productInfoMapper.updateById(po);
            }
        }
        return ResultBody.success();
    }
}
