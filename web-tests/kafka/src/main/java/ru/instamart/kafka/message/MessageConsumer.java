package ru.instamart.kafka.message;

public interface MessageConsumer<T> {
    void consume(Message<T> message);

}
