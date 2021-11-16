package ru.instamart.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kafka.message.BaseMessage;
import ru.instamart.kafka.message.MessageConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Collections.singletonList;
import static ru.instamart.kafka.utils.BrokerUtil.createClientId;

public class KafkaConsumers <T>{
    private final String topic;

    private final Consumer<String, T> consumer;

    private final AtomicBoolean shutdown;

    private final CountDownLatch shutdownLatch;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    @SuppressWarnings("unchecked")
    public KafkaConsumers(final KafkaConfig config) {

        topic = config.topic;

        shutdown = new AtomicBoolean(false);

        shutdownLatch = new CountDownLatch(1);

        Properties props = new Properties();

        props.put(ConsumerConfig.CLIENT_ID_CONFIG, createClientId());

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapServerString());

        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.groupName);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.serdeClassName);

        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");

        props.put("schema.registry.url", config.bootstrapServerString());


        consumer = new KafkaConsumer<>(props);
    }

    public void shutdown() throws Exception {
        shutdown.set(true);
        shutdownLatch.await(4, TimeUnit.SECONDS);
        executor.shutdown();
    }

    public void consume(MessageConsumer<T> consume) {
        executor.submit(() -> {
            try {
                consumer.subscribe(singletonList(topic));

                while (!shutdown.get()) {
                    ConsumerRecords<String, T> messages = consumer.poll(Duration.ofSeconds(2));

                    messages.forEach(msg -> {
                        BaseMessage<T> message = new BaseMessage<>();
                        message.setPayload(msg.value());
                        message.ack = () -> {};
                        consume.consume(message);
                    });
                }
            } finally {
                consumer.close();
                shutdownLatch.countDown();
            }
        });
    }

    public Consumer<String, T> getLastMessage(){
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }
}
