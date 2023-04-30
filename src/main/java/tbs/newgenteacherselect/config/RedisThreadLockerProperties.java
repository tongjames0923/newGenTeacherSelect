package tbs.newgenteacherselect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author abstergo
 */

@ConfigurationProperties(prefix = "tbs.thread.locker.redis")
public class RedisThreadLockerProperties {

    private boolean enable=false;
    private long timeout=30;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
