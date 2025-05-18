package com.kains.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 */
@Configuration
@ConfigurationProperties(prefix = "message.queue")
public class MessageQueueConfig {

    /**
     * 默认队列初始容量
     */
    private int defaultCapacity = 11;

    /**
     * 默认消息优先级
     */
    private int defaultPriority = 5;

    /**
     * 默认超时时间（毫秒）
     */
    private long defaultTimeout = 1000;

    /**
     * 是否启用消息队列功能
     */
    private boolean enabled = true;

    public int getDefaultCapacity() {
        return defaultCapacity;
    }

    public void setDefaultCapacity(int defaultCapacity) {
        this.defaultCapacity = defaultCapacity;
    }

    public int getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(int defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public long getDefaultTimeout() {
        return defaultTimeout;
    }

    public void setDefaultTimeout(long defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
