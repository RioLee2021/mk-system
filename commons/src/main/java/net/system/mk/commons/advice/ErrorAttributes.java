package net.system.mk.commons.advice;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.expr.GlobalErrorCode;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author USER
 */
@Component
@Slf4j
public class ErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> attributes = super.getErrorAttributes(webRequest, options);
        // 根据路径来输出
        Object path = attributes.get("path");
        if (path != null) {
            String str = path.toString();
            if (!(str.equals("/") || str.equals("/csrf"))) {
                log.error("Springboot内置错误提示信息：{}", JSON.toJSONString(attributes));
            }
        }

        Map<String, Object> result = new HashMap<>(2);
        result.put("code", GlobalErrorCode.SPRINGBOOT_GLOBAL.getCode());
        result.put("msg", attributes.get("error") == null ? "system.exception" : attributes.get("error"));
        return result;
    }
}
