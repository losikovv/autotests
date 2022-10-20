package ru.instamart.kafkaesque;

import ru.instamart.kafkaesque.consumer.KafkaesqueConsumerDSL;
import ru.instamart.kafkaesque.producer.KafkaesqueProducerDSL;

public final class Kafkaesque {
  
  private final KafkaConf conf;
  
  public static Kafkaesque at(final KafkaConf conf) {
    return new Kafkaesque(conf);
  }

  private Kafkaesque(final KafkaConf conf) {
    this.conf = conf;
  }
  
  public <Key, Value> KafkaesqueConsumerDSL<Key, Value> consume() {
    return KafkaesqueConsumerDSL.newInstance(conf);
  }

  public <Key, Value> KafkaesqueProducerDSL<Key, Value> produce() {
    return KafkaesqueProducerDSL.newInstance(conf);
  }
}
