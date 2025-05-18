package com.kains.pojo;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 通用消息模型
 * @param <T> 消息内容的类型
 */
public class Message<T> implements Comparable<Message<T>> {
    // 消息唯一标识
    private final String id;
    // 消息优先级（数字越小优先级越高）
    private final int priority;
    // 消息内容
    private final T payload;
    // 消息创建时间
    private final LocalDateTime timestamp;

    public Message(T payload, int priority) {
        this.id = UUID.randomUUID().toString();
        this.payload = payload;
        this.priority = priority;
        this.timestamp = LocalDateTime.now();
    }

    public Message(T payload) {
        this(payload, 5); // 默认优先级为5
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public T getPayload() {
        return payload;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Message<T> other) {
        // 优先级比较：数字小的优先级高
        int priorityCompare = Integer.compare(this.priority, other.priority);
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        // 如果优先级相同，按时间戳排序（先进先出）
        return this.timestamp.compareTo(other.timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", priority=" + priority +
                ", payload=" + payload +
                ", timestamp=" + timestamp +
                '}';
    }
}
