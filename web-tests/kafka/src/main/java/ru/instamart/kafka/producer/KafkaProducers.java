package ru.instamart.kafka.producer;

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer;
import io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import ru.instamart.kafka.KafkaConfig;

import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;

import static ru.instamart.kafka.utils.BrokerUtil.createClientId;

@Slf4j
public class KafkaProducers<T> {

    private final String topic;

    private final Producer<String, T> producer;

    public KafkaProducers(final KafkaConfig config) {

        topic = config.topic;

        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, createClientId());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapServerString());
        props.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        //props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");


        if (config.exactlyOnce) {
            props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        } else {
            props.put(ProducerConfig.ACKS_CONFIG, config.acks);
            props.put(ProducerConfig.RETRIES_CONFIG, config.retries);
        }

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaProtobufSerializer.class.getName());

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.serdeClassName);

        props.put("schema.registry.url", config.bootstrapServerString());


        producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    public void publish(T msg) {

        // TODO: partition key?
        ProducerRecord<String, T> record = new ProducerRecord<>(topic, msg);

        long start = System.currentTimeMillis();

        producer.send(record, (metadata, exception) -> {
            long elapsedTime = System.currentTimeMillis() - start;
            if (metadata != null) {
                log.debug(String.format("sent record(key=%s value=%s) meta(partition=%d, offset=%d) time=%d",
                        record.key(), record.value(), metadata.partition(),
                        metadata.offset(), elapsedTime));
            }
            if (exception != null) {
                log.error("Failed sending message to kafka", exception);
            }
        });
    }

    public void publish(Collection<T> messages) {
        for (T message : messages) {
            publish(message);
        }
    }

    public void shutdown() {
        if (producer != null) {
            producer.close();
        }
    }
}
