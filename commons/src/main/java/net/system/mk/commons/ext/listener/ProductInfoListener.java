package net.system.mk.commons.ext.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Getter;
import net.system.mk.commons.ext.ProductInfoExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 20:05
 */
@Getter
public class ProductInfoListener extends AnalysisEventListener<ProductInfoExcel> {

    private List<ProductInfoExcel> cacheDataList = new ArrayList<>();

    @Override
    public void invoke(ProductInfoExcel productInfoExcel, AnalysisContext analysisContext) {
        cacheDataList.add(productInfoExcel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
