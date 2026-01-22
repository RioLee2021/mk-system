package net.system.mk.commons.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import net.system.mk.commons.anno.QueryCon;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.PagerRequest;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;


/**
 * @author USER
 */
public class OtherUtils {



    /**
     * 解码私钥字符串
     */
    public static PrivateKey decodePrivateKey(String privateKeyText) {
        // 检查PEM格式
        if (!privateKeyText.startsWith("-----BEGIN PRIVATE KEY-----") &&
                !privateKeyText.startsWith("-----BEGIN RSA PRIVATE KEY-----")) {
            throw new GlobalException(BUSINESS_ERROR, "私钥格式错误");
        }

        try {
            String content = null;
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            if (privateKeyText.startsWith("-----BEGIN PRIVATE KEY-----")) {
                // PKCS#8 格式
                content = privateKeyText.replace("-----BEGIN PRIVATE KEY-----", "")
                        .replace("-----END PRIVATE KEY-----", "")
                        .replaceAll("\\s", "");
            }

            if (privateKeyText.startsWith("-----BEGIN RSA PRIVATE KEY-----")) {
                // PKCS#1 格式
                content = privateKeyText.replace("-----BEGIN RSA PRIVATE KEY-----", "")
                        .replace("-----END RSA PRIVATE KEY-----", "")
                        .replaceAll("\\s", "");
            }

            if (content == null) {
                throw new GlobalException(BUSINESS_ERROR, "私钥无效");
            }

            // 将文本转换为 private key 对象
            byte[] keyBytes = Base64.getDecoder().decode(content);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, e.getMessage());
        }
    }

    /**
     * 使用私钥解密
     */
    public static String decryptByPrivateKey(String privateKeyText, String encryptedData) {
        try {
            PrivateKey privateKey = decodePrivateKey(privateKeyText);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, "私钥异常，解密失败");
        }
    }


    public static void easyExport(HttpServletResponse response,String filename,List<?> data, Class<?> clazz){
        if (!filename.endsWith(".xlsx")){
            filename = filename.substring(0, filename.lastIndexOf("."))+"-"+System.currentTimeMillis()+".xlsx";
        }
        try {
            WriteCellStyle headerStyle = new WriteCellStyle();
            headerStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            WriteCellStyle contentStyle = new WriteCellStyle();
            contentStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            filename = URLEncoder.encode(filename, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + filename + ";");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).registerWriteHandler(new HorizontalCellStyleStrategy(headerStyle, contentStyle)).build();
            WriteSheet sheet = EasyExcel.writerSheet("充值订单").head(clazz).build();
            excelWriter.write(data, sheet);
            excelWriter.finish();
        } catch (IOException e) {
            throw new GlobalException(BUSINESS_ERROR, e.getMessage());
        }
    }

    public static PublicKey decodePublicKey(String pk) {
        // 检查PEM格式
        if (!pk.startsWith("-----BEGIN PUBLIC KEY-----")&&!pk.startsWith("-----BEGIN RSA PUBLIC KEY-----")){
            throw new GlobalException(BUSINESS_ERROR, "公钥格式错误");
        }
        // 解析公钥
        try {
            String content=null;
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (pk.startsWith("-----BEGIN PUBLIC KEY-----")){
                content = pk.replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");
            }
            if (pk.startsWith("-----BEGIN RSA PUBLIC KEY-----")){
                content  = pk.replace("-----BEGIN RSA PUBLIC KEY-----", "")
                        .replace("-----END RSA PUBLIC KEY-----", "")
                        .replaceAll("\\s", "");
            }
            if (content==null){
                throw new GlobalException(BUSINESS_ERROR, "公钥无效");
            }
            // 将文本转换为 public key 对象
            byte[] keyBytes = Base64.getDecoder().decode(content);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, e.getMessage());
        }
    }

    public static String encryptByPublicKey(String publicKeyText,String data){
        //用公钥加密
        try {
            PublicKey pk = decodePublicKey(publicKeyText);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, "公钥异常，加密失败");
        }
    }

    public static boolean verifySignature(String publicKeyText,String data,String signature){
        try {
            PublicKey pk = decodePublicKey(publicKeyText);
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(pk);
            sign.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return sign.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, "签名异常，校验失败");
        }
    }

    public static boolean verifyDeviceSignature(String publicKeyText,Object data,String signature,String deviceUk){
        String preStr = EncryptUtil.preSignObject(data);
        String str = preStr+deviceUk;
        return verifySignature(publicKeyText,str,signature);
    }

    public static String fmtString(String str, Object... args) {
        return MessageFormatter.arrayFormat(str, args).getMessage();
    }

    public static String namedFormat(String str,Object arg) {
        BeanWrapper bw = new BeanWrapperImpl(arg);
        PropertyDescriptor[] pds = bw.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds){
            if (bw.isReadableProperty(pd.getName())){
                Object val = bw.getPropertyValue(pd.getName());
                String placeholder = "\\{"+Pattern.quote(pd.getName())+"\\}";
                str = str.replaceAll(placeholder,String.valueOf(val));
            }
        }
        return str;
    }


    // IP的正则
    private static Pattern pattern = Pattern
            .compile("(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\."
                    + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})\\." + "(1\\d{1,2}|2[0-4]\\d|25[0-5]|\\d{1,2})");
    private static final String DEFAULT_ALLOW_ALL_FLAG = "*";    // 允许所有ip标志位
    private static final String DEFAULT_DENY_ALL_FLAG = "0";    // 禁止所有ip标志位

    /**
     * 根据IP白名单设置获取可用的IP列表
     *
     * @param allowIp
     * @return
     */
    private static Set<String> getAllowedIpList(String allowIp) {
        String[] splitRex = allowIp.split(";");// 拆分出白名单正则
        Set<String> ipList = new HashSet<String>(splitRex.length);
        for (String allow : splitRex) {
            allow = allow.trim();
            if (allow.contains("*")) {// 处理通配符 *
                String[] ips = allow.split("\\.");
                String[] from = new String[]{"0", "0", "0", "0"};
                String[] end = new String[]{"255", "255", "255", "255"};
                List<String> tem = new ArrayList<String>();
                for (int i = 0; i < ips.length; i++) {
                    if (ips[i].indexOf("*") > -1) {
                        tem = complete(ips[i]);
                        from[i] = null;
                        end[i] = null;
                    } else {
                        from[i] = ips[i];
                        end[i] = ips[i];
                    }
                }

                StringBuilder fromIP = new StringBuilder();
                StringBuilder endIP = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    if (from[i] != null) {
                        fromIP.append(from[i]).append(".");
                        endIP.append(end[i]).append(".");
                    } else {
                        fromIP.append("[*].");
                        endIP.append("[*].");
                    }
                }
                fromIP.deleteCharAt(fromIP.length() - 1);
                endIP.deleteCharAt(endIP.length() - 1);

                for (String s : tem) {
                    String ip = fromIP.toString().replace("[*]", s.split(";")[0]) + "-"
                            + endIP.toString().replace("[*]", s.split(";")[1]);
                    if (validate(ip)) {
                        ipList.add(ip);
                    }
                }
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                ipList.add(allow);
            } else {// 处理单个 ip 或者 范围
                if (validate(allow)) {
                    ipList.add(allow);
                }
            }
        }
        return ipList;
    }

    /**
     * 对单个IP节点进行范围限定
     *
     * @param arg
     * @return 返回限定后的IP范围，格式为List[10;19, 100;199]
     */
    private static List<String> complete(String arg) {
        List<String> com = new ArrayList<String>();
        int len = arg.length();
        if (len == 1) {
            com.add("0;255");
        } else if (len == 2) {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
            String s2 = complete(arg, 2);
            if (s2 != null) {
                com.add(s2);
            }
        } else {
            String s1 = complete(arg, 1);
            if (s1 != null) {
                com.add(s1);
            }
        }
        return com;
    }

    private static String complete(String arg, int length) {
        String from = "";
        String end = "";
        if (length == 1) {
            from = arg.replace("*", "0");
            end = arg.replace("*", "9");
        } else {
            from = arg.replace("*", "00");
            end = arg.replace("*", "99");
        }
        if (Integer.valueOf(from) > 255) {
            return null;
        }
        if (Integer.valueOf(end) > 255) {
            end = "255";
        }
        return from + ";" + end;
    }

    /**
     * 在添加至白名单时进行格式校验
     *
     * @param ip
     * @return
     */
    private static boolean validate(String ip) {
        String[] temp = ip.split("-");
        for (String s : temp) {
            if (!pattern.matcher(s).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据IP,及可用Ip列表来判断ip是否包含在白名单之中
     *
     * @param ip
     * @param ipList
     * @return
     */
    private static boolean isPermitted(String ip, Set<String> ipList) {
        if (ipList.isEmpty() || ipList.contains(ip)) {
            return true;
        }
        for (String allow : ipList) {
            if (allow.indexOf("-") > -1) {// 处理 类似 192.168.0.0-192.168.2.1
                String[] tempAllow = allow.split("-");
                String[] from = tempAllow[0].split("\\.");
                String[] end = tempAllow[1].split("\\.");
                String[] tag = ip.split("\\.");
                boolean check = true;
                for (int i = 0; i < 4; i++) {// 对IP从左到右进行逐段匹配
                    int s = Integer.valueOf(from[i]);
                    int t = Integer.valueOf(tag[i]);
                    int e = Integer.valueOf(end[i]);
                    if (!(s <= t && t <= e)) {
                        check = false;
                        break;
                    }
                }
                if (check) {
                    return true;
                }
            } else if (allow.contains("/")) {// 处理 网段 xxx.xxx.xxx./24
                int splitIndex = allow.indexOf("/");
                // 取出子网段
                String ipSegment = allow.substring(0, splitIndex); // 192.168.3.0
                // 子网数
                String netmask = allow.substring(splitIndex + 1);// 24
                // ip 转二进制
                long ipLong = ipToLong(ip);
                //子网二进制
                long maskLong = (2L << 32 - 1) - (2L << Integer.valueOf(32 - Integer.valueOf(netmask)) - 1);
                // ip与和子网相与 得到 网络地址
                String calcSegment = longToIP(ipLong & maskLong);
                // 如果计算得出网络地址和库中网络地址相同 则合法
                if (ipSegment.equals(calcSegment)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据IP地址，及IP白名单设置规则判断IP是否包含在白名单
     *
     * @param ip
     * @param ipWhiteConfig
     * @return
     */
    public static boolean isPermitted(String ip, String ipWhiteConfig) {
        if (null == ip || "".equals(ip) || null == ipWhiteConfig) {
            return false;
        }
        //ip格式不对
        if (!pattern.matcher(ip).matches()) {
            return false;
        }
        if (DEFAULT_ALLOW_ALL_FLAG.equals(ipWhiteConfig)) {
            return true;
        }
        if (DEFAULT_DENY_ALL_FLAG.equals(ipWhiteConfig)) {
            return false;
        }
        Set<String> ipList = getAllowedIpList(ipWhiteConfig.replaceAll("；", ";"));
        return isPermitted(ip, ipList);
    }

    private static long ipToLong(String strIP) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    // 将10进制整数形式转换成127.0.0.1形式的IP地址
    private static String longToIP(long longIP) {
        StringBuilder sb = new StringBuilder("");
        // 直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }



    public static void printTablePartitionSqlByDays(String tableName, String column, LocalDate begin, LocalDate end, int per) {
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(tableName).append(" PARTITION BY RANGE (UNIX_TIMESTAMP(").append(column).append(")) (\n");
        while (begin.isBefore(end)) {
            String name = "p" + begin.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String date = begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String sql = messageFormat("PARTITION {} VALUES LESS THAN (UNIX_TIMESTAMP('{} 00:00:00')),\n", name, date);
            sb.append(sql);
            begin = begin.plusDays(per);
        }
        sb.setLength(sb.length() - 2);
        sb.append("\n);");
        System.out.println(sb);
    }


    public static void copyProperties(Object source, Object target, boolean ignoreNull, boolean ignoreEmpty, String... ignoreProperties) {
        BeanWrapper sw = new BeanWrapperImpl(source);
        BeanWrapper tw = new BeanWrapperImpl(target);
        PropertyDescriptor[] spds = sw.getPropertyDescriptors();
        PropertyDescriptor[] tpds = tw.getPropertyDescriptors();
        for (PropertyDescriptor spd : spds) {
            if("class".equalsIgnoreCase(spd.getName())) {
                continue;
            }
            boolean pass = false;
            for (String ignoreProperty : ignoreProperties) {
                if (spd.getName().equals(ignoreProperty)) {
                    pass = true;
                    break;
                }
            }
            if (pass) {
                continue;
            }
            for (PropertyDescriptor tpd : tpds) {
                if (spd.getName().equals(tpd.getName())) {
                    Object sv = sw.getPropertyValue(spd.getName());
                    if (ignoreNull && sv == null) {
                        continue;
                    }
                    if (ignoreEmpty && StrUtil.isEmptyIfStr(sv)) {
                        continue;
                    }
                    tw.setPropertyValue(tpd.getName(), sv);
                }
            }
        }
    }

    private static boolean checkNullOfMybatis(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return StrUtil.isBlank(o.toString());
        }
        if (o instanceof Collection) {
            return ((Collection<?>) o).size() == 0;
        }
        return false;
    }

    public static <T> QueryWrapper<T> createIdDescWrapper(Object obj) {
        return createWrapper(obj, "id", true);
    }

    public static <T> QueryWrapper<T> createOrderByWrapper(Object obj, String orderBy,boolean desc) {
        return createWrapper(obj, orderBy, desc);
    }

    public static <T> QueryWrapper<T> createOrderByWrapper(Object obj, String orderBy,boolean desc, String alias) {
        String ob = orderBy.contains(".")?orderBy:alias+"."+orderBy;
        return createWrapper(obj, ob, desc, alias);
    }

    public static <T> QueryWrapper<T> createIdDescWrapper(Object obj, String alias) {
        return createWrapper(obj, alias+".id", true, alias);
    }


    public static <T> QueryWrapper<T> createWrapper(Object obj, String orderBy, boolean desc) {
        QueryWrapper<T> q = createWrapper(obj);
        return desc ? q.orderByDesc(orderBy) : q.orderByAsc(orderBy);
    }

    public static <T> QueryWrapper<T> createWrapper(Object obj, String orderBy, boolean desc, String alias) {
        QueryWrapper<T> q = createWrapper(obj, alias);
        if (q == null) {
            return null;
        }
        return desc ? q.orderByDesc(orderBy) : q.orderByAsc(orderBy);
    }

    public static <T> QueryWrapper<T> createWrapper(Object obj) {
        return createWrapper(obj, null);
    }

    public static <T> QueryWrapper<T> createWrapper(Object obj, String alias) {
        if (obj!=null&& PagerRequest.class.getName().equals(obj.getClass().getName())) {
            return Wrappers.emptyWrapper();
        }
        QueryWrapper<T> res = Wrappers.query();
        BeanWrapper bean = new BeanWrapperImpl(obj);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            QueryCon qc = field.getAnnotation(QueryCon.class);
            if (qc != null) {
                String name = "".equals(qc.name()) ? StrUtil.toUnderlineCase(field.getName()) : StrUtil.toUnderlineCase(qc.name());
                if (alias != null && !name.contains(".")) {
                    //如果没有设置备注字段名且有别名前缀时
                    name = alias + "." + name;
                }
                Object val = bean.getPropertyValue(field.getName());
                switch (qc.op()) {
                    case eq:
                        if (qc.require()) {
                            res.eq(name, val);
                        } else {
                            res.eq(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case ne:
                        if (qc.require()) {
                            res.ne(name, val);
                        } else {
                            res.ne(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case like:
                        if (qc.require()) {
                            res.like(name, val);
                        } else {
                            res.like(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case notLike:
                        if (qc.require()) {
                            res.notLike(name, val);
                        } else {
                            res.notLike(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case ge:
                        if (qc.require()) {
                            res.ge(name, val);
                        } else {
                            res.ge(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case gt:
                        if (qc.require()) {
                            res.gt(name, val);
                        } else {
                            res.gt(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case le:
                        if (qc.require()) {
                            res.le(name, val);
                        } else {
                            res.le(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case lt:
                        if (qc.require()) {
                            res.lt(name, val);
                        } else {
                            res.lt(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case notIn:
                        if (qc.require()) {
                            res.notIn(name, val);
                        } else {
                            res.notIn(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case in:
                        if (qc.require()) {
                            res.in(name, val);
                        } else {
                            res.in(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case likeLeft:
                        if (qc.require()) {
                            res.likeLeft(name, val);
                        } else {
                            res.likeLeft(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case likeRight:
                        if (qc.require()) {
                            res.likeRight(name, val);
                        } else {
                            res.likeRight(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case isNull:
                        res.isNull(name);
                        break;
                    case isNotNull:
                        res.isNotNull(name);
                        break;
                }
                if (qc.order()) {
                    if (qc.asc()) {
                        res.orderByAsc(name);
                    } else {
                        res.orderByDesc(name);
                    }
                }
            }
        }
        return res;
    }

    public static <T>UpdateWrapper<T> createUpdateWrapper(Object obj,String alias){
        if (obj!=null&& PagerRequest.class.getName().equals(obj.getClass().getName())) {
            return Wrappers.update();
        }
        UpdateWrapper<T> res = Wrappers.update();
        BeanWrapper bean = new BeanWrapperImpl(obj);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            QueryCon qc = field.getAnnotation(QueryCon.class);
            if (qc != null) {
                String name = "".equals(qc.name()) ? StrUtil.toUnderlineCase(field.getName()) : StrUtil.toUnderlineCase(qc.name());
                if (alias != null && !name.contains(".")) {
                    //如果没有设置备注字段名且有别名前缀时
                    name = alias + "." + name;
                }
                Object val = bean.getPropertyValue(field.getName());
                switch (qc.op()) {
                    case eq:
                        if (qc.require()) {
                            res.eq(name, val);
                        } else {
                            res.eq(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case ne:
                        if (qc.require()) {
                            res.ne(name, val);
                        } else {
                            res.ne(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case like:
                        if (qc.require()) {
                            res.like(name, val);
                        } else {
                            res.like(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case notLike:
                        if (qc.require()) {
                            res.notLike(name, val);
                        } else {
                            res.notLike(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case ge:
                        if (qc.require()) {
                            res.ge(name, val);
                        } else {
                            res.ge(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case gt:
                        if (qc.require()) {
                            res.gt(name, val);
                        } else {
                            res.gt(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case le:
                        if (qc.require()) {
                            res.le(name, val);
                        } else {
                            res.le(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case lt:
                        if (qc.require()) {
                            res.lt(name, val);
                        } else {
                            res.lt(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case notIn:
                        if (qc.require()) {
                            res.notIn(name, val);
                        } else {
                            res.notIn(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case in:
                        if (qc.require()) {
                            res.in(name, val);
                        } else {
                            res.in(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case likeLeft:
                        if (qc.require()) {
                            res.likeLeft(name, val);
                        } else {
                            res.likeLeft(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case likeRight:
                        if (qc.require()) {
                            res.likeRight(name, val);
                        } else {
                            res.likeRight(!checkNullOfMybatis(val), name, val);
                        }
                        break;
                    case isNull:
                        res.isNull(name);
                        break;
                    case isNotNull:
                        res.isNotNull(name);
                        break;
                }
                if (qc.order()) {
                    if (qc.asc()) {
                        res.orderByAsc(name);
                    } else {
                        res.orderByDesc(name);
                    }
                }
            }
        }
        return res;
    }

    public static String messageFormat(String message, Object... args) {
        return MessageFormatter.arrayFormat(message, args).getMessage();
    }


    public static String bean2print(String prefix, Object bean) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(" >>> \n");
        if (bean != null) {
            List<Object> args = Lists.newArrayList();
            Field[] fields = bean.getClass().getDeclaredFields();
            BeanWrapper wrapper = new BeanWrapperImpl(bean);
            for (Field field : fields) {
                if (field.isAnnotationPresent(ApiModelProperty.class)) {
                    ApiModelProperty amp = field.getAnnotation(ApiModelProperty.class);
                    sb.append(amp.value()).append("：{}\n");
                    args.add(wrapper.getPropertyValue(field.getName()));
                }
            }
            sb.append(" <<< \n");
            if (args.size() > 0) {
                return messageFormat(sb.toString(), args.toArray());
            }
        }
        return prefix;
    }
}
