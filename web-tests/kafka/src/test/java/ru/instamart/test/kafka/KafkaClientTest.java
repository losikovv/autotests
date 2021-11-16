package ru.instamart.test.kafka;

import lombok.SneakyThrows;
import org.testng.annotations.Test;
import ru.instamart.kafka.KafkaBroker;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kafka.consumer.KafkaConsumers;


public class KafkaClientTest {

    @SneakyThrows
    @Test(groups = {"kafka-instamart-regress"},
            description = "first test")
    public void test() {
        KafkaConfig kafkaConfig = new KafkaConfig();
        KafkaBroker kafkaBroker = new KafkaBroker(kafkaConfig);
        KafkaConsumers consumer = kafkaBroker.getConsumer();
        var stringListMap = consumer.getLastMessage();
    }
}
