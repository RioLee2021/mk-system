package net.system.mk.commons.ext;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.system.mk.commons.pojo.ProductInfo;

import java.math.BigDecimal;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 19:59
 */
@Data
@Accessors(chain = true)
public class ProductInfoExcel {

    @ExcelProperty(value = "产品名称",index = 0)
    private String productName;

    @ExcelProperty(value = "产品图片1",index = 1)
    private String pic1Url;

    @ExcelProperty(value = "产品图片2",index = 2)
    private String pic2Url;

    @ExcelProperty(value = "产品图片3",index = 3)
    private String pic3Url;

    @ExcelProperty(value = "标签价格",index = 4)
    private String labelPrice;

    @ExcelProperty(value = "拼价格",index = 5)
    private String orderPrice;

    public ProductInfo toProductInfo(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName(productName)
                .setPic1Url(pic1Url).setPic2Url(pic2Url).setPic3Url(pic3Url)
                .setLabelPrice(new BigDecimal(labelPrice)).setOrderPrice(new BigDecimal(orderPrice));
        return productInfo;
    }
}
