package com.kains.service.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息队列工厂类
 * 用于创建和管理不同类型的消息队列实例
 */
public class MessageQueueFactory {

    // 单例实例
    private static final MessageQueueFactory INSTANCE = new MessageQueueFactory();

    // 存储不同名称的消息队列实例
    private final Map<String, MessageQueue<?>> queueMap = new ConcurrentHashMap<>();

    // 私有构造函数，防止外部实例化
    private MessageQueueFactory() {}

    /**
     * 获取工厂实例
     * @return 消息队列工厂实例
     */
    public static MessageQueueFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 创建或获取指定名称的消息队列
     * @param queueName 队列名称
     * @param <T> 消息内容的类型
     * @return 消息队列实例
     */
    @SuppressWarnings("unchecked")
    public <T> MessageQueue<T> getQueue(String queueName) {
        return (MessageQueue<T>) queueMap.computeIfAbsent(queueName, name -> new InMemoryMessageQueue<>());
    }

    /**
     * 创建或获取指定名称和初始容量的消息队列
     * @param queueName 队列名称
     * @param initialCapacity 初始容量
     * @param <T> 消息内容的类型
     * @return 消息队列实例
     */
    @SuppressWarnings("unchecked")
    public <T> MessageQueue<T> getQueue(String queueName, int initialCapacity) {
        return (MessageQueue<T>) queueMap.computeIfAbsent(queueName, name -> new InMemoryMessageQueue<>(initialCapacity));
    }

    /**
     * 移除指定名称的消息队列
     * @param queueName 队列名称
     * @return 被移除的消息队列，如果不存在则返回null
     */
    @SuppressWarnings("unchecked")
    public <T> MessageQueue<T> removeQueue(String queueName) {
        return (MessageQueue<T>) queueMap.remove(queueName);
    }

    /**
     * 检查指定名称的消息队列是否存在
     * @param queueName 队列名称
     * @return 如果存在则返回true，否则返回false
     */
    public boolean containsQueue(String queueName) {
        return queueMap.containsKey(queueName);
    }

    /**
     * 获取所有队列名称
     * @return 所有队列名称的数组
     */
    public String[] getQueueNames() {
        return queueMap.keySet().toArray(new String[0]);
    }

    /**
     * 清空所有队列
     */
    public void clearAllQueues() {
        queueMap.values().forEach(MessageQueue::clear);
    }
}
