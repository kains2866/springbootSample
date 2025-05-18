package com.kains.service.queue;

import com.kains.pojo.Message;

/**
 * 消息队列接口
 * @param <T> 消息内容的类型
 */
public interface MessageQueue<T> {

    /**
     * 发送消息到队列
     * @param message 要发送的消息
     * @return 是否发送成功
     */
    boolean send(Message<T> message);

    /**
     * 接收并移除队列中的下一条消息
     * @return 下一条消息，如果队列为空则返回null
     */
    Message<T> receive();

    /**
     * 查看队列中的下一条消息，但不移除
     * @return 下一条消息，如果队列为空则返回null
     */
    Message<T> peek();

    /**
     * 获取队列中的消息数量
     * @return 队列中的消息数量
     */
    int size();

    /**
     * 检查队列是否为空
     * @return 如果队列为空则返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * 清空队列
     */
    void clear();
}
