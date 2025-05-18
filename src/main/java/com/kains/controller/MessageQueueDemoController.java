package com.kains.controller;

import com.kains.pojo.Message;
import com.kains.service.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 消息队列示例控制器
 */
@RestController
@RequestMapping("/api/queue")
public class MessageQueueDemoController {

    @Autowired
    private MessageQueueService messageQueueService;

    /**
     * 发送消息到指定队列
     * @param queueName 队列名称
     * @param content 消息内容
     * @param priority 优先级（可选）
     * @return 操作结果
     */
    @PostMapping("/{queueName}/send")
    public Map<String, Object> sendMessage(
            @PathVariable String queueName,
            @RequestParam String content,
            @RequestParam(required = false, defaultValue = "5") int priority) {

        boolean success = messageQueueService.sendMessage(queueName, content, priority);

        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("queueName", queueName);
        result.put("queueSize", messageQueueService.getQueueSize(queueName));
        return result;
    }

    /**
     * 从指定队列接收消息
     * @param queueName 队列名称
     * @param timeout 等待超时时间（毫秒，可选）
     * @return 消息内容
     */
    @GetMapping("/{queueName}/receive")
    public Map<String, Object> receiveMessage(
            @PathVariable String queueName,
            @RequestParam(required = false) Long timeout) {

        Message<?> message;
        try {
            if (timeout != null) {
                message = messageQueueService.receiveMessage(queueName, timeout, TimeUnit.MILLISECONDS);
            } else {
                message = messageQueueService.receiveMessage(queueName);
            }
        } catch (InterruptedException e) {
            message = null;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", message != null);
        result.put("queueName", queueName);
        result.put("message", message);
        result.put("queueSize", messageQueueService.getQueueSize(queueName));
        return result;
    }

    /**
     * 查看指定队列的状态
     * @param queueName 队列名称
     * @return 队列状态信息
     */
    @GetMapping("/{queueName}/status")
    public Map<String, Object> getQueueStatus(@PathVariable String queueName) {
        Map<String, Object> status = new HashMap<>();
        status.put("queueName", queueName);
        status.put("size", messageQueueService.getQueueSize(queueName));
        status.put("empty", messageQueueService.isQueueEmpty(queueName));
        status.put("nextMessage", messageQueueService.peekMessage(queueName));
        return status;
    }

    /**
     * 清空指定队列
     * @param queueName 队列名称
     * @return 操作结果
     */
    @DeleteMapping("/{queueName}/clear")
    public Map<String, Object> clearQueue(@PathVariable String queueName) {
        messageQueueService.clearQueue(queueName);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("queueName", queueName);
        result.put("queueSize", 0);
        return result;
    }

    /**
     * 获取所有队列名称
     * @return 队列列表
     */
    @GetMapping("/list")
    public Map<String, Object> listQueues() {
        String[] queueNames = messageQueueService.getQueueNames();

        Map<String, Object> result = new HashMap<>();
        result.put("queues", queueNames);
        result.put("count", queueNames.length);
        return result;
    }
}
