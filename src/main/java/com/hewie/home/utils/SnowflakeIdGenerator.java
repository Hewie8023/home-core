package com.hewie.home.utils;

import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdGenerator implements IdGenerator {

    private final AtomicLong lastTimestamp = new AtomicLong(0L);
    private final long workerId;
    private final long datacenterId;
    private final AtomicLong sequence = new AtomicLong(0L);

    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    @Override
    public long nextId() {
        long timestamp = timeGen();
        while (timestamp < lastTimestamp.get()) {
            timestamp = timeGen();
        }

        if (timestamp != lastTimestamp.get()) {
            lastTimestamp.set(timestamp);
            sequence.set(0L);
        }

        long sequenceMask = -1L ^ (1L << 12);
        long sequenceNum = sequence.getAndIncrement() & sequenceMask;

        return ((timestamp - 1288834974657L) << 22) |
                (workerId << 17) |
                (datacenterId << 12) |
                sequenceNum;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
