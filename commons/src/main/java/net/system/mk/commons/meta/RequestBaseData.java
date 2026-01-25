package net.system.mk.commons.meta;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import lombok.Getter;
import net.system.mk.commons.utils.WebUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author USER
 * @date 2025-04-2025/4/14/0014 13:16
 */
@Getter
public class RequestBaseData {

    public static final String TRACE_ID = "TraceId";

    public static final String AUTHORIZATION = "Authorization";

    public static final String DEVICE = "Device";

    public static final String MERCHANT_ID = "MerchantId";

    public static final String MERCHANT_CODE = "MerchantCode";

    public static final String ACCEPT_LANGUAGE = "Accept-Language";

    private String traceId;

    private String uri;

    private String token;

    private String ip;

    private String device;

    private Integer merchantId;

    private String basePath;

    private String userAgent;

    private String merchantCode;

    private String language;

    private RequestBaseData(){}

    public static RequestBaseData getInstance(){
        RequestBaseData rs = new RequestBaseData();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Object mid = request.getAttribute(MERCHANT_ID);
        String serverName = request.getServerName();
        if (serverName.matches("^\\w+-.*$")){
            rs.merchantCode = serverName.split("-")[0];
        }else {
            rs.merchantCode = "undefined";
        }
        rs.merchantId = Convert.toInt(mid,-1);
        rs.basePath = request.getScheme()+"://"+request.getServerName();
        rs.uri = request.getRequestURI();
        rs.ip = WebUtils.getRemoteAddress(request);
        rs.token = request.getHeader(AUTHORIZATION);
        rs.language = request.getHeader(ACCEPT_LANGUAGE);
        rs.token = rs.getToken()==null?"undefined":rs.getToken();
        Object tid = request.getAttribute(TRACE_ID);
        rs.traceId = tid==null? "tce-"+ IdUtil.fastSimpleUUID() :tid.toString();
        rs.device = request.getHeader(DEVICE);
        rs.device = rs.getDevice()==null?"undefined":rs.getDevice();
        request.setAttribute(TRACE_ID,rs.traceId);
        rs.userAgent = request.getHeader("User-Agent");
        rs.userAgent = rs.getUserAgent() == null ? "undefined" : rs.getUserAgent();
        return rs;
    }
}
