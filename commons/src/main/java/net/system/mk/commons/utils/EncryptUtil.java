package net.system.mk.commons.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author USER
 */
@Slf4j
public class EncryptUtil {

    public static void main(String[] args) throws Exception {

    }

    public static final  String SALT = "usdtspayspayusdt";

    public static final AES aes = new AES(Mode.ECB, Padding.PKCS5Padding,SALT.getBytes());

    public static String signature(Object content,String key){
        return signature(content,key,null);
    }

    public static String signature(Object content,String key,String... ignoreFields){
        String str = preSignObject(content,ignoreFields);
        return DigestUtil.sha256Hex(str+key);
    }

    public static String preSignObject(Object content,String... ignoreFields){
        JSONObject json = (JSONObject) JSON.toJSON(content);
        Map<String,String> treeMap = new TreeMap<>();
        json.keySet().forEach(k->{
            if (json.getString(k)!=null){
                if (json.get(k) instanceof BigDecimal){
                    treeMap.put(k,json.getBigDecimal(k).stripTrailingZeros().toPlainString());
                }else {
                    treeMap.put(k, json.getString(k));
                }
            }else {
                treeMap.put(k,"");
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> entry: treeMap.entrySet()){
            boolean ignored = false;
            if (ignoreFields!=null){
                for (String field:ignoreFields){
                    if (entry.getKey().equals(field)){
                        ignored = true;
                        break;
                    }
                }
            }
            if (ignored || "s".equalsIgnoreCase(entry.getKey())){
                //跳过忽略字段和签名字段
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString();
    }


    /**
     * @param str    需要修正的字符串
     * @param ch     用什么修正
     * @param length 需要的长度
     * @param left   是否从左补或从左删除
     * @return 修正好的字符串
     */
    private static String fixLengthByChar(String str, char ch, int length, boolean left) {
        int off = length - str.length();
        if (off > 0) {
            //需要补
            StringBuilder strBuilder = new StringBuilder(str);
            for (int i = 0; i < off; i++) {
                if (left) {
                    strBuilder.insert(0, ch);
                } else {
                    strBuilder.append(ch);
                }
            }
            return strBuilder.toString();
        }
        if (off < 0) {
            //需要切
            if (left) {
                return str.substring(Math.abs(off));
            } else {
                return str.substring(0, length);
            }
        }
        return str;
    }

    /**
     * aes加密
     *
     * @param content 待加密数据
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {
        return aes.encryptBase64(content);
    }

    /**
     * aes解密
     *
     * @param content 待解密数据
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String content) throws Exception {
        return aes.decryptStr(content);
    }
}
