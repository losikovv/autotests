package ru.instamart.kafka;

import lombok.Data;
import lombok.Getter;
import ru.instamart.kafka.consumer.KafkaConsumers;
import ru.instamart.kafka.message.Message;
import ru.instamart.kafka.message.MessageBroker;
import ru.instamart.kafka.message.MessageConsumer;
import ru.instamart.kafka.producer.KafkaProducers;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Data
@Getter
public class KafkaBroker<T> implements MessageBroker<T> {
    private final KafkaProducers<T> producer;
    private final KafkaConsumers<T> consumer;

    public KafkaBroker(KafkaConfig config) {
        this.producer = new KafkaProducers<>(config);
        this.consumer = new KafkaConsumers<>(config);

    }

    @Override
    public void publish(Message<T> message) throws Exception {
        producer.publish(message.getPayload());
    }

    @Override
    public void publish(Collection<Message<T>> messages) throws Exception {
        producer.publish(messages.stream()
                .map(Message::getPayload)
                .collect(toList()));
    }

    @Override
    public void consume(MessageConsumer<T> consume) throws Exception {
        consumer.consume(consume);
    }

    @Override
    public void startup() throws Exception {
    }

    @Override
    public void shutdown() throws Exception {
        producer.shutdown();
        consumer.shutdown();
    }

    @Override
    public void getLastMessage() throws Exception {
        consumer.getLastMessage();
    }
}
