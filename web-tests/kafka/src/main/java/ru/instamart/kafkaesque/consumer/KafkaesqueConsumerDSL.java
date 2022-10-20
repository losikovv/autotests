package ru.instamart.kafkaesque.consumer;

import ru.instamart.kafkaesque.KafkaConf;
import ru.instamart.kafkaesque.consumer.KafkaesqueConsumer.DelegateCreationInfo;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Creates instances of {@link KafkaesqueConsumer}.<br/> There are defaults for some properties. In
 * details, we have the following:
 *
 * <ol>
 *   <li>{@code waitingAtMost(200L, TimeUnit.MILLISECONDS)}</li>
 *   <li>{@code waitingEmptyPolls(2, 50L, TimeUnit.MILLISECONDS)}</li>
 * </ol>
 *
 * @param <Key>   The type of the key of a message that the consumer can read
 * @param <Value> The type of the value of a message that the consumer can read
 */
public class KafkaesqueConsumerDSL<Key, Value> {
  
  private final KafkaConf conf;
  private String topic;
  private Deserializer<Key> keyDeserializer;
  private Deserializer<Value> valueDeserializer;
  private long interval = 60;
  private TimeUnit timeUnit = TimeUnit.SECONDS;
  private int emptyPollsCount = 2;
  private long emptyPollsInterval = 50L;
  private TimeUnit emptyPollsTimeUnit = TimeUnit.MILLISECONDS;
  
  private KafkaesqueConsumerDSL(KafkaConf conf) {
    this.conf = conf;
    this.topic = conf.getTopic();
    this.keyDeserializer = (Deserializer<Key>) conf.getKeyDeserializer();
    this.valueDeserializer = (Deserializer<Value>) conf.getValueDeserializer();
  }
  
  public static <Key, Value> KafkaesqueConsumerDSL<Key, Value> newInstance(KafkaConf conf) {
    return new KafkaesqueConsumerDSL<>(conf);
  }
  

  /**
   * Sets the topic to read from. This information is mandatory.
   *
   * @param topic The topic name
   */
  public KafkaesqueConsumerDSL<Key, Value> fromTopic(String topic) {
    this.topic = topic;
    return this;
  }
  
  /**
   * Sets the key and value deserializers. This information is mandatory.
   *
   * @param keyDeserializer   The key deserializer
   * @param valueDeserializer The value deserializer
   */
  public KafkaesqueConsumerDSL<Key, Value> withDeserializers(
      Deserializer<Key> keyDeserializer, Deserializer<Value> valueDeserializer) {
    this.keyDeserializer = keyDeserializer;
    this.valueDeserializer = valueDeserializer;
    return this;
  }
  
  /**
   * Sets the time interval to wait until the receipt of all the produced messages. This information
   * is optional. The default values are {@code 200L} and {@code TimeUnit.MILLISECOND}.
   *
   * @param interval Time interval
   * @param unit     Unit of the time interval
   */
  public KafkaesqueConsumerDSL<Key, Value> waitingAtMost(long interval, TimeUnit unit) {
    this.interval = interval;
    this.timeUnit = unit;
    return this;
  }
  
  /**
   * Sets the number of times a poll should return an empty list of messages to consider the read
   * phase concluded. This information is optional. The default values are {@code 2}, {@code 50L},
   * and {@code TimeUnit.MILLISECONDS}.
   *
   * @param count           Number of empty polls
   * @param waitingInterval The interval to wait between two poll operations
   * @param waitingTimeUnit The time unit of the above interval
   */
  public KafkaesqueConsumerDSL<Key, Value> waitingEmptyPolls(
      int count, long waitingInterval, TimeUnit waitingTimeUnit) {
    this.emptyPollsCount = count;
    this.emptyPollsInterval = waitingInterval;
    this.emptyPollsTimeUnit = waitingTimeUnit;
    return this;
  }
  
  /**
   * Creates an instance of the {@link KafkaesqueConsumer} and polls for messages. Before the
   * creation, it performs a set of validation steps.
   *
   * @return An instance of polled messages
   * @see AssertionsOnConsumedDelegate
   * @see AssertionsOnConsumed
   */
  public AssertionsOnConsumedDelegate<Key, Value> expectingConsumed() {
    validateInputs();
    final DelegateCreationInfo<Key, Value> creationInfo =
        new DelegateCreationInfo<>(topic, keyDeserializer, valueDeserializer);
    final KafkaesqueConsumer<Key, Value> consumer = new KafkaesqueConsumer<>(
        conf,
        interval,
        timeUnit,
        emptyPollsCount,
        emptyPollsInterval,
        emptyPollsTimeUnit,
        creationInfo);
    return consumer.poll();
  }
  
  private void validateInputs() {
    validateTopic();
    validateDeserializers();
  }
  
  private void validateTopic() {
    if (topic == null || topic.isBlank()) {
      throw new IllegalArgumentException("The topic name cannot be empty");
    }
  }
  
  private void validateDeserializers() {
    if (keyDeserializer == null || valueDeserializer == null) {
      throw new IllegalArgumentException("The deserializers cannot be null");
    }
  }
}
