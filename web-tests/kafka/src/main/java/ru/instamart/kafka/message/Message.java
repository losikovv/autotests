package ru.instamart.kafka.message;

public interface Message<T> {

    T getPayload();

    void setPayload(T payload);

    long getTimestamp();

    void setTimestamp(long timestamp);

    void ack();
}
