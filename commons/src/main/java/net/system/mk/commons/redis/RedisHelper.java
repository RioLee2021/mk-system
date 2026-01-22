package net.system.mk.commons.redis;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author USER
 */
@Component
public class RedisHelper {

    private static final String GAT_PREFIX = "GetAndTouch:";

    @Resource
    private RedisTemplate redisTemplate;

    public boolean hasKey(String codeKey,String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(Joiner.on(":").join(codeKey,key)));
    }

    private boolean setIfAbsent(String key,Object val,long timeout){
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key,val,timeout, TimeUnit.SECONDS));
    }

    public <T> void set(String codeKey, String key, T val){
        set(Joiner.on(":").join(codeKey,key),val);
    }

    private <T> void set(String key,T val){
        redisTemplate.opsForValue().set(key,val);
    }

    public <T> void set(String codeKey,String key,T val,long timeout,TimeUnit unit){
        set(Joiner.on(":").join(codeKey,key),val,timeout,unit);
    }

    private <T> void set(String key,T val,long timeout,TimeUnit unit){
        redisTemplate.opsForValue().set(key,val,timeout,unit);
    }

    public <T> void setTouch(String codeKey,Object key,T val,long timeout,TimeUnit unit){
        setTouch(Joiner.on(":").join(codeKey,key),val,timeout,unit);
    }

    private <T> void setTouch(String key,T val,long timeout,TimeUnit unit){
        GATime gat = new GATime(timeout, unit);
        if (timeout>0) {
            redisTemplate.opsForValue().set(key, val, timeout, unit);
            redisTemplate.opsForValue().set(GAT_PREFIX + key, gat, timeout, unit);
        }else {
            redisTemplate.opsForValue().set(key,val);
            redisTemplate.opsForValue().set(GAT_PREFIX,gat);
        }
    }

    public <T> T get(String codeKey,String key){
        return get(Joiner.on(":").join(codeKey,key));
    }

    public <T> T getAndTouch(String codeKey,String key){
        return getAndTouch(Joiner.on(":").join(codeKey,key));
    }

    public <T> List<T> getPrefix(String prefix){
        List<T> rs = Lists.newArrayList();
        Set<String> keys = redisTemplate.keys(prefix + "*");
        for (String key : keys) {
            T val = get(key);
            if (val != null) {
                rs.add(val);
            }
        }
        return rs;
    }

    private <T> T get(String key){
        ValueOperations<String,T> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }


    private <T> T getAndTouch(String key){
        //获取GAT信息
        GATime gat = this.get(GAT_PREFIX+key);
        T val = this.get(key);
        if (gat!=null&&val!=null){
            this.setTouch(key,val,gat.getTimeout(),gat.getUnit());
        }
        return val;
    }

    private boolean hasKey(String key){
        return key != null && Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean delete(String codeKey,String key){
        return delete(Joiner.on(":").join(codeKey,key));
    }

    private boolean delete(String key){
        return key!=null && Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean deleteTouch(String codeKey,String key){
        return deleteTouch(Joiner.on(":").join(codeKey,key));
    }

    private boolean deleteTouch(String key){
        this.delete(GAT_PREFIX+key);
        return this.delete(key);
    }

    public boolean expire(String codeKey,String key,long timeout,TimeUnit unit){
        return expire(Joiner.on(":").join(codeKey,key),timeout,unit);
    }

    private boolean expire(String key,long timeout,TimeUnit unit){
        return key!=null&&unit!=null&& Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    public Long getExpire(String codeKey,Object key,TimeUnit unit){
        return getExpire(Joiner.on(":").join(codeKey,key),unit);
    }

    private Long getExpire(String key,TimeUnit unit){
        if (key==null||unit==null){
            return -2L;
        }
        return redisTemplate.getExpire(key,unit);
    }

    public Long deleteByPrefix(String prefix){
        Set keys = redisTemplate.keys(prefix + "*");
        if (keys!=null&&keys.size()>0){
            return redisTemplate.delete(keys);
        }
        return -1L;
    }

    public Integer scanPrefix(String prefix){
        Set keys = redisTemplate.keys(prefix + "*");
        return keys==null?0:keys.size();
    }
}
