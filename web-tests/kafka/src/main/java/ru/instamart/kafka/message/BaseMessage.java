package ru.instamart.kafka.message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseMessage <T> implements Message<T>{
    private T payload;
    private long timestamp = System.currentTimeMillis();

    public MessageBroker.Ack ack = () -> log.error("ACKING NOT ENABLED");

    @Override
    public T getPayload() {
        return payload;
    }

    @Override
    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public void ack() {
        ack.ack();
    }
}
