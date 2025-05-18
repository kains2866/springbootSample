# 消息队列模块使用说明

## 概述

这是一个轻量级的内存消息队列模块，支持以下特性：

- 支持任意类型的消息内容
- 支持消息优先级
- 线程安全，支持并发操作
- 支持超时等待
- 支持多个命名队列

## 核心组件

1. **Message<T>** - 通用消息模型
2. **MessageQueue<T>** - 消息队列接口
3. **InMemoryMessageQueue<T>** - 基于内存的优先级队列实现
4. **MessageQueueFactory** - 消息队列工厂
5. **MessageQueueService** - Spring服务类
6. **MessageQueueConfig** - 配置类

## 使用方法

### 1. 配置

在`application.properties`或`application.yml`中可以配置消息队列参数：

```yaml
message:
  queue:
    enabled: true
    default-capacity: 100
    default-priority: 5
    default-timeout: 1000
```

### 2. 发送消息

#### 使用Spring服务

```java
@Autowired
private MessageQueueService messageQueueService;

// 发送简单消息（默认优先级）
messageQueueService.sendMessage("myQueue", "Hello World");

// 发送带优先级的消息（数字越小优先级越高）
messageQueueService.sendMessage("myQueue", "Important Message", 1);

// 发送自定义对象消息
User user = new User("John", "Doe");
messageQueueService.sendMessage("userQueue", user);
```

#### 直接使用队列

```java
MessageQueueFactory factory = MessageQueueFactory.getInstance();
MessageQueue<String> queue = factory.getQueue("myQueue");

Message<String> message = new Message<>("Hello World", 3);
queue.send(message);
```

### 3. 接收消息

#### 使用Spring服务

```java
// 非阻塞接收（如果队列为空则返回null）
Message<?> message = messageQueueService.receiveMessage("myQueue");

// 带超时的接收
try {
    Message<?> message = messageQueueService.receiveMessage("myQueue", 5000, TimeUnit.MILLISECONDS);
    if (message != null) {
        System.out.println("Received: " + message.getPayload());
    } else {
        System.out.println("No message received within timeout");
    }
} catch (InterruptedException e) {
    // 处理中断异常
}
```

#### 直接使用队列

```java
MessageQueue<String> queue = MessageQueueFactory.getInstance().getQueue("myQueue");

// 非阻塞接收
Message<String> message = queue.receive();

// 带超时的接收（仅适用于InMemoryMessageQueue）
if (queue instanceof InMemoryMessageQueue) {
    InMemoryMessageQueue<String> memoryQueue = (InMemoryMessageQueue<String>) queue;
    try {
        Message<String> message = memoryQueue.receive(5000, TimeUnit.MILLISECONDS);
        // 处理消息
    } catch (InterruptedException e) {
        // 处理中断异常
    }
}
```

### 4. 查看队列状态

```java
// 获取队列大小
int size = messageQueueService.getQueueSize("myQueue");

// 检查队列是否为空
boolean isEmpty = messageQueueService.isQueueEmpty("myQueue");

// 查看下一条消息但不移除
Message<?> nextMessage = messageQueueService.peekMessage("myQueue");

// 获取所有队列名称
String[] queueNames = messageQueueService.getQueueNames();
```

### 5. 管理队列

```java
// 清空队列
messageQueueService.clearQueue("myQueue");

// 移除队列
messageQueueService.removeQueue("myQueue");

// 清空所有队列
messageQueueService.clearAllQueues();
```

## REST API

模块提供了一个示例控制器，可以通过HTTP API操作消息队列：

- **发送消息**: `POST /api/queue/{queueName}/send?content=hello&priority=1`
- **接收消息**: `GET /api/queue/{queueName}/receive?timeout=5000`
- **查看队列状态**: `GET /api/queue/{queueName}/status`
- **清空队列**: `DELETE /api/queue/{queueName}/clear`
- **列出所有队列**: `GET /api/queue/list`

## 示例代码

### 生产者-消费者模式

```java
// 生产者线程
new Thread(() -> {
    for (int i = 0; i < 10; i++) {
        String message = "Message " + i;
        messageQueueService.sendMessage("workQueue", message, i % 3);
        System.out.println("Produced: " + message);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}).start();

// 消费者线程
new Thread(() -> {
    while (true) {
        try {
            Message<?> message = messageQueueService.receiveMessage("workQueue", 5000, TimeUnit.MILLISECONDS);
            if (message != null) {
                System.out.println("Consumed: " + message.getPayload() + " (Priority: " + message.getPriority() + ")");
            } else {
                System.out.println("No messages available, consumer exiting");
                break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            break;
        }
    }
}).start();
```

### 使用自定义对象

```java
public class Task implements Comparable<Task> {
    private String name;
    private int priority;
    
    // 构造函数、getter和setter省略
    
    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }
}

// 发送任务
Task task = new Task("ImportantTask", 1);
messageQueueService.sendMessage("taskQueue", task);

// 接收任务
Message<Task> message = messageQueueService.receiveMessage("taskQueue");
if (message != null) {
    Task task = message.getPayload();
    System.out.println("Processing task: " + task.getName());
}
```

## 注意事项

1. 这是一个内存队列，应用重启后数据会丢失
2. 适用于单实例应用，不支持分布式场景
3. 对于大量数据或需要持久化的场景，建议使用专业的消息中间件如RabbitMQ、Kafka等
4. 消息优先级是数字越小优先级越高
