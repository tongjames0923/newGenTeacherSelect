package tbs.newgenteacherselect.config.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tbs.Utils.Async.IThreadLocker;
import tbs.Utils.Async.IThreadSign;

import javax.annotation.Resource;

@Component
public class RedisThreadLocker implements IThreadLocker {


    @Resource
    RedisTemplate<String,Object> redisTemplate;
    @Override
    public boolean isLock(IThreadSign sign) {
        return redisTemplate.opsForValue().get(sign.key())!=null;
    }

    @Override
    public void lock(IThreadSign sign) {
        redisTemplate.opsForValue().set(sign.key(),true);
    }

    @Override
    public void unlock(IThreadSign sign) {
        redisTemplate.delete(sign.key());
    }
}
