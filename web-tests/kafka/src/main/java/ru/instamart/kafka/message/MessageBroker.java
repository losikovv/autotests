package ru.instamart.kafka.message;

import java.util.Collection;

public interface MessageBroker <T>{
    void publish(Message<T> message) throws Exception;

    void publish(Collection<Message<T>> messages) throws Exception;

    void consume(MessageConsumer<T> consumer) throws Exception;

    void startup() throws Exception;

    void shutdown() throws Exception;

    void getLastMessage() throws Exception;

    @FunctionalInterface
    interface Ack {
        void ack();
    }
}
