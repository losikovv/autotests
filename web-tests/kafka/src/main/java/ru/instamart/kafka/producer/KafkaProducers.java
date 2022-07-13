package ru.instamart.kafka.producer;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kraken.config.CoreProperties;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class KafkaProducers {

    private static KafkaProducer<String, byte[]> kafkaProducer ;
    private static String saslConfigs = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";

    private Properties producerProperties(KafkaConfig config) {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.clientId);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CoreProperties.KAFKA_SERVER);

        //protobuf
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);

        //auth
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.jaas.config", String.format(saslConfigs, config.login, config.password));
        return props;
    }

    private void createProducer(final KafkaConfig config) {
        Properties props = producerProperties(config);
        kafkaProducer = new KafkaProducer<>(props);
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void publish(final KafkaConfig config, byte[] msg) {
        createProducer(config);
        ProducerRecord<String, byte[]> record = new ProducerRecord(config.topic, msg);

        long start = System.currentTimeMillis();

        kafkaProducer.send(record, (metadata, exception) -> {
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

    public void shutdown() {
        if (kafkaProducer != null) {
            kafkaProducer.close();
        }
    }
}
