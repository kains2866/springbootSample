package com.kains.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/04/2025/4/29
 * @Description:
 */

public class SnowflakeIdGenerator {
    // 起始时间戳
    private final static long EPOCH = 1577836800000L;

    // 各部分的位数
    private final static long DATA_CENTER_ID_BITS = 5L;
    private final static long WORKER_ID_BITS = 5L;
    private final static long SEQUENCE_BITS = 12L;

    // 最大值（位移运算计算）
    private final static long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    // 各部分的位移量
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private final static long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private final long dataCenterId;  // 数据中心ID
    private final long workerId;      // 机器ID
    private long sequence = 0L;       // 序列号
    private long lastTimestamp = -1L; // 上次生成ID的时间戳

    public SnowflakeIdGenerator() {
        this(0L, 0L);
    }

    public SnowflakeIdGenerator(long dataCenterId, long workerId) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID 超出范围");
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID 超出范围");
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        // 如果当前时间小于上次时间，说明时钟回拨
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨，拒绝生成ID");
        }

        // 同一毫秒内生成多个ID
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 序列号溢出（同一毫秒内超过4096个ID）
            if (sequence == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L; // 新的一毫秒，重置序列号
        }

        lastTimestamp = currentTimestamp;

        // 组合各部分生成最终ID
        return ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    // 等待下一毫秒（解决序列号溢出）
    private long tilNextMillis(long lastTimestamp) {
        long currentTimestamp = System.currentTimeMillis();
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = System.currentTimeMillis();
        }
        return currentTimestamp;
    }
}