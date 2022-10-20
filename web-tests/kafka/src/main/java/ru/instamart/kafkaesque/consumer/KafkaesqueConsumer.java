package ru.instamart.kafkaesque.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.Deserializer;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import ru.instamart.kafkaesque.KafkaConf;
import ru.instamart.kraken.config.CoreProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a consumer that can read messages with key of type {@code Key}, and value of type
 * {@code Value}.
 *
 * @param <Key>   Type of the key of a message
 * @param <Value> Type of the value of a message
 * @see KafkaesqueConsumerDSL
 */
@Slf4j
public class KafkaesqueConsumer<Key, Value> {

    private final KafkaConsumer<Key, Value> kafkaConsumer;

    private final long interval;
    private final TimeUnit timeUnit;
    private final int emptyPollsCount;
    private final long emptyPollsInterval;
    private final TimeUnit emptyPollsTimeUnit;

    private final DelegateCreationInfo<Key, Value> creationInfo;
    private static String saslConfigs = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";

    KafkaesqueConsumer(
            KafkaConf conf,
            long interval,
            TimeUnit timeUnit,
            int emptyPollsCount,
            long emptyPollsInterval,
            TimeUnit emptyPollsTimeUnit,
            DelegateCreationInfo<Key, Value> creationInfo) {
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.emptyPollsCount = emptyPollsCount;
        this.emptyPollsInterval = emptyPollsInterval;
        this.emptyPollsTimeUnit = emptyPollsTimeUnit;
        this.creationInfo = creationInfo;
        this.kafkaConsumer = createKafkaConsumer(conf);
    }

    private KafkaConsumer<Key, Value> createKafkaConsumer(KafkaConf conf) {
        String saslConfig = String.format(saslConfigs, conf.getLogin(), conf.getPassword());
        final Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kraken");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CoreProperties.KAFKA_SERVER);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, creationInfo.getKeyDeserializer().getClass());
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                creationInfo.getValueDeserializer().getClass());
        //auth
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.jaas.config", saslConfig);

        final KafkaConsumer<Key, Value> consumer = new KafkaConsumer<>(props);
        subscribeConsumerToTopic(consumer, creationInfo.getTopic());
        return consumer;
    }

    private void subscribeConsumerToTopic(KafkaConsumer<Key, Value> consumer, String topic) {
        CountDownLatch latch = new CountDownLatch(1);
        consumer.subscribe(
                List.of(topic),
                new ConsumerRebalanceListener() {
                    @Override
                    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    }

                    @Override
                    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                        latch.countDown();
                    }
                });
        Awaitility.await()
                .atMost(1, TimeUnit.MINUTES)
                .until(
                        () -> {
                            // The actual assignment of a topic to a consumer is done after a while
                            // the consumer starts to poll messages. So, we forced the consumer to poll
                            // from the topic, and we wait until the consumer is assigned to the topic.
                            try {
                                consumer.poll(Duration.ofMillis(100));
                            } catch (Exception e) {
                                // Ignore every exception during polling. We are only interested in the assignment
                            }
                            final boolean assigned = latch.getCount() == 0;
                            if (assigned) {
                                log.info("Resetting the offset to beginning");
                                consumer.seekToBeginning(consumer.assignment());
                            }
                            return assigned;
                        });
    }

    /**
     * Polls the broker and reads the messages contained in the configured topic.
     *
     * @return The read messages
     */
    AssertionsOnConsumedDelegate<Key, Value> poll() {
        final List<ConsumerRecord<Key, Value>> readMessages = new ArrayList<>();
        try {
            final AtomicInteger emptyCycles = new AtomicInteger(emptyPollsCount);
            log.debug("Empty cycles to await: " + emptyPollsCount);
            Awaitility.await()
                    .atMost(interval, timeUnit)
                    .pollInterval(emptyPollsInterval, emptyPollsTimeUnit)
                    .until(
                            () -> {
                                if (isEmptyPollAfterSomeMessagesWereRead(readMessages)) {
                                    final int remainingCycles = emptyCycles.decrementAndGet();
                                    log.debug("Remaining empty cycles: " + remainingCycles);
                                    log.debug(String.valueOf(System.currentTimeMillis()));
                                    return remainingCycles == 0;
                                }
                                return false;
                            });
            return new AssertionsOnConsumedDelegate<>(new AssertionsOnConsumed<>(readMessages), this);
        } catch (ConditionTimeoutException ctex) {
            if (readMessages.isEmpty()) {
                throw new AssertionError(
                        String.format(
                                "The consumer cannot find any message during the given time interval: %d %s",
                                interval, timeUnit.toString()));
            } else {
                throw new AssertionError(
                        String.format(
                                "The consumer reads new messages until the end of the given time interval: %d %s",
                                interval, timeUnit.toString()));
            }
        } catch (Exception ex) {
            throw new KafkaesqueConsumerPollException("Error during the poll operation", ex);
        }
    }

    private boolean isEmptyPollAfterSomeMessagesWereRead(List<ConsumerRecord<Key, Value>> readMessages) {
        return readNewMessages(readMessages) == 0 && !readMessages.isEmpty();
    }

    private int readNewMessages(List<ConsumerRecord<Key, Value>> readMessages) {
        final ConsumerRecords<Key, Value> polled = kafkaConsumer.poll(Duration.ofMillis(50L));
        log.debug("Polled: " + polled.count());
        final List<ConsumerRecord<Key, Value>> newMessages = new ArrayList<>();
        polled.records(creationInfo.getTopic()).forEach(newMessages::add);
        if (!newMessages.isEmpty()) {
            readMessages.addAll(newMessages);
        }
        return newMessages.size();
    }

    /**
     * Closes the consumer. After the closing operation, the consumer cannot read any more messages.
     */
    public void close() {
        kafkaConsumer.close();
    }

    /**
     * Information needed to create a concrete Kafka consumer:
     *
     * <ul>
     *   <li>A topic
     *   <li>A key deserializer
     *   <li>A value deserializer
     * </ul>
     *
     * @param <Key>   The type of the messages' key
     * @param <Value> The type of the messages' value
     */
    static class DelegateCreationInfo<Key, Value> {
        private final String topic;
        private final Deserializer<Key> keyDeserializer;
        private final Deserializer<Value> valueDeserializer;

        DelegateCreationInfo(
                String topic, Deserializer<Key> keyDeserializer, Deserializer<Value> valueDeserializer) {
            this.topic = topic;
            this.keyDeserializer = keyDeserializer;
            this.valueDeserializer = valueDeserializer;
        }

        public String getTopic() {
            return topic;
        }

        public Deserializer<Key> getKeyDeserializer() {
            return keyDeserializer;
        }

        public Deserializer<Value> getValueDeserializer() {
            return valueDeserializer;
        }
    }
}
