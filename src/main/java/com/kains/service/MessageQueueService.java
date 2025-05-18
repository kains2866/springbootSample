package com.kains.service;

import com.kains.pojo.Message;
import com.kains.service.queue.MessageQueue;
import com.kains.service.queue.MessageQueueFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 消息队列服务
 * 提供应用程序与消息队列交互的接口
 */
@Service
public class MessageQueueService {

    private final MessageQueueFactory queueFactory;

    public MessageQueueService() {
        this.queueFactory = MessageQueueFactory.getInstance();
    }

    /**
     * 发送消息到指定队列
     * @param queueName 队列名称
     * @param message 消息对象
     * @param <T> 消息内容的类型
     * @return 是否发送成功
     */
    public <T> boolean sendMessage(String queueName, Message<T> message) {
        MessageQueue<T> queue = queueFactory.getQueue(queueName);
        return queue.send(message);
    }

    /**
     * 发送消息内容到指定队列（使用默认优先级）
     * @param queueName 队列名称
     * @param payload 消息内容
     * @param <T> 消息内容的类型
     * @return 是否发送成功
     */
    public <T> boolean sendMessage(String queueName, T payload) {
        Message<T> message = new Message<>(payload);
        return sendMessage(queueName, message);
    }

    /**
     * 发送消息内容到指定队列，并指定优先级
     * @param queueName 队列名称
     * @param payload 消息内容
     * @param priority 优先级（数字越小优先级越高）
     * @param <T> 消息内容的类型
     * @return 是否发送成功
     */
    public <T> boolean sendMessage(String queueName, T payload, int priority) {
        Message<T> message = new Message<>(payload, priority);
        return sendMessage(queueName, message);
    }

    /**
     * 从指定队列接收消息
     * @param queueName 队列名称
     * @param <T> 消息内容的类型
     * @return 消息对象，如果队列为空则返回null
     */
    public <T> Message<T> receiveMessage(String queueName) {
        MessageQueue<T> queue = queueFactory.getQueue(queueName);
        return queue.receive();
    }

    /**
     * 从指定队列接收消息，如果队列为空则等待指定的时间
     * @param queueName 队列名称
     * @param timeout 超时时间
     * @param unit 时间单位
     * @param <T> 消息内容的类型
     * @return 消息对象，如果超时则返回null
     * @throws InterruptedException 如果等待过程中被中断
     */
    public <T> Message<T> receiveMessage(String queueName, long timeout, TimeUnit unit) throws InterruptedException {
        MessageQueue<T> queue = queueFactory.getQueue(queueName);
        if (queue instanceof com.kains.service.queue.InMemoryMessageQueue) {
            return ((com.kains.service.queue.InMemoryMessageQueue<T>) queue).receive(timeout, unit);
        }
        return queue.receive();
    }

    /**
     * 查看指定队列中的下一条消息，但不移除
     * @param queueName 队列名称
     * @param <T> 消息内容的类型
     * @return 消息对象，如果队列为空则返回null
     */
    public <T> Message<T> peekMessage(String queueName) {
        MessageQueue<T> queue = queueFactory.getQueue(queueName);
        return queue.peek();
    }

    /**
     * 获取指定队列中的消息数量
     * @param queueName 队列名称
     * @return 队列中的消息数量
     */
    public int getQueueSize(String queueName) {
        MessageQueue<?> queue = queueFactory.getQueue(queueName);
        return queue.size();
    }

    /**
     * 检查指定队列是否为空
     * @param queueName 队列名称
     * @return 如果队列为空则返回true，否则返回false
     */
    public boolean isQueueEmpty(String queueName) {
        MessageQueue<?> queue = queueFactory.getQueue(queueName);
        return queue.isEmpty();
    }

    /**
     * 清空指定队列
     * @param queueName 队列名称
     */
    public void clearQueue(String queueName) {
        MessageQueue<?> queue = queueFactory.getQueue(queueName);
        queue.clear();
    }

    /**
     * 移除指定队列
     * @param queueName 队列名称
     */
    public void removeQueue(String queueName) {
        queueFactory.removeQueue(queueName);
    }

    /**
     * 获取所有队列名称
     * @return 所有队列名称的数组
     */
    public String[] getQueueNames() {
        return queueFactory.getQueueNames();
    }

    /**
     * 清空所有队列
     */
    public void clearAllQueues() {
        queueFactory.clearAllQueues();
    }
}
