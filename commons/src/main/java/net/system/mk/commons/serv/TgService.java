package net.system.mk.commons.serv;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.redis.RedisHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;


/**
 * @author USER
 * @date 2025-07-2025/7/18/0018 7:44
 */
@Service
@Slf4j
public class TgService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private AppConfig appConfig;

    public synchronized void sendPrivateMessage(String chatId,String message){
        sendPrivateMessage(appConfig.getTgBotToken(), chatId, message);
    }


    public synchronized void sendPrivateMessage(String apiKey, String chatId,String message) {
        Integer count = redisHelper.get(RedisCodeKey.Common.TG_BOT_COUNT, apiKey);
        if (count!=null && count >= 29){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            int value = count == null ? 1 : count + 1;
            redisHelper.setTouch(RedisCodeKey.Common.TG_BOT_COUNT, apiKey, value, 1, TimeUnit.SECONDS);
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chat_id", chatId);
        params.add("text", message);
        String url = "https://api.telegram.org/bot" + apiKey + "/sendMessage";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.queryParams(params).build().encode().toUri();
        try {
            ResponseEntity<JSONObject> rs = restTemplate.getForEntity(uri, JSONObject.class);
            if (!rs.getStatusCode().is2xxSuccessful()) {
                log.error("发送失败:{}",rs.getBody());
                throw new GlobalException(BUSINESS_ERROR, "发送失败");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, "发送异常：" + e.getMessage());
        }
    }

    public void sendKeyboardMessage(String apiKey,String chatId,String message,String button,String urls){
        String keyboard = "{\"inline_keyboard\":[[{\"text\":\"" + button + "\",\"url\":\"" + urls + "\"}]]}";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("chat_id", chatId);
        params.add("text", message);
        params.add("reply_markup", keyboard);
        String url = "https://api.telegram.org/bot" + apiKey + "/sendMessage";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.queryParams(params).build().encode().toUri();
        try {
            ResponseEntity<JSONObject> rs = restTemplate.getForEntity(uri, JSONObject.class);
            if (!rs.getStatusCode().is2xxSuccessful()) {
                log.error("发送失败:{}",rs.getBody());
                throw new GlobalException(BUSINESS_ERROR, "发送失败");
            }
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new GlobalException(BUSINESS_ERROR, "发送异常：" + e.getMessage());
        }
    }
}
