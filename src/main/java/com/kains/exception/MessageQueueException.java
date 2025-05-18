package com.kains.exception;

/**
 * 消息队列异常类
 */
public class MessageQueueException extends RuntimeException {

    public MessageQueueException(String message) {
        super(message);
    }

    public MessageQueueException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageQueueException(Throwable cause) {
        super(cause);
    }
}