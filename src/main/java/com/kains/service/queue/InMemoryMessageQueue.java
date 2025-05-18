package com.kains.service.queue;

import com.kains.pojo.Message;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 基于内存的消息队列实现
 * @param <T> 消息内容的类型
 */
public class InMemoryMessageQueue<T> implements MessageQueue<T> {

    // 使用PriorityBlockingQueue实现优先级队列，并保证线程安全
    private final PriorityBlockingQueue<Message<T>> queue;

    // 队列容量
    private final int capacity;

    /**
     * 创建一个指定初始容量的内存消息队列
     * @param initialCapacity 初始容量
     */
    public InMemoryMessageQueue(int initialCapacity) {
        this.queue = new PriorityBlockingQueue<>(initialCapacity);
        this.capacity = initialCapacity;
    }

    /**
     * 创建一个默认初始容量(11)的内存消息队列
     */
    public InMemoryMessageQueue() {
        this(11); // 默认初始容量为11
    }

    @Override
    public boolean send(Message<T> message) {
        if (message == null) {
            return false;
        }
        return queue.offer(message);
    }

    /**
     * 发送消息到队列，并指定超时时间
     * @param message 要发送的消息
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 是否发送成功
     * @throws InterruptedException 如果等待过程中被中断
     */
    public boolean send(Message<T> message, long timeout, TimeUnit unit) throws InterruptedException {
        if (message == null) {
            return false;
        }
        return queue.offer(message, timeout, unit);
    }

    @Override
    public Message<T> receive() {
        return queue.poll();
    }

    /**
     * 接收并移除队列中的下一条消息，如果队列为空则等待指定的时间
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 下一条消息，如果超时则返回null
     * @throws InterruptedException 如果等待过程中被中断
     */
    public Message<T> receive(long timeout, TimeUnit unit) throws InterruptedException {
        return queue.poll(timeout, unit);
    }

    @Override
    public Message<T> peek() {
        return queue.peek();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public void clear() {
        queue.clear();
    }

    /**
     * 获取队列的容量
     * @return 队列容量
     */
    public int getCapacity() {
        return capacity;
    }
}
