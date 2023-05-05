package tbs.newgenteacherselect.config.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tbs.newgenteacherselect.config.RedisThreadLockerProperties;
import tbs.utils.Async.interfaces.IThreadLocker;
import tbs.utils.Async.interfaces.IThreadSign;
import tbs.utils.redis.IRedisService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author abstergo
 */
@Component
@Scope("prototype")
@ConditionalOnProperty(prefix = "tbs.thread.locker.redis", name = "enable", havingValue = "true")
public class RedisThreadLocker implements IThreadLocker {
    @Autowired
    RedisThreadLockerProperties properties;


    @Resource
    IRedisService redisService;

    @Override
    public boolean isLock(IThreadSign sign) {
        return redisService.hasKey(sign.key()) && redisService.getExpire(sign.key(), TimeUnit.MINUTES) != -1;
    }

    @Override
    public void lock(IThreadSign sign) {
        redisService.set(sign.key(), null);
    }

    @Override
    public void unlock(IThreadSign sign) {
        redisService.expire(sign.key(), properties.getTimeout(), TimeUnit.SECONDS);
    }

    @Override
    public <T> void putObject(IThreadSign sign, T obj) {
        if (isLock(sign)) {
            redisService.set(sign.key(), obj);
        }
    }

    @Override
    public <T> T getObject(IThreadSign sign, Class<? extends T> clas) {
        return redisService.get(sign.key(), clas);
    }

    @Override
    public <T> List<T> getList(IThreadSign sign, Class<T> clas) {
        return redisService.getList(sign.key(), clas);
    }
}
