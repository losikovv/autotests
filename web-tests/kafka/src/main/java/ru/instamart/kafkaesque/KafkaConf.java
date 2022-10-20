package ru.instamart.kafkaesque;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.kafka.common.serialization.StringDeserializer;
import ru.instamart.kafkaesque.deserializer.OrderEventOrderDeserializer;
import ru.instamart.kraken.common.Crypt;

@RequiredArgsConstructor
@Getter
@ToString
public enum KafkaConf {
    ORDER_SERVICE(
            "yc.operations-order-service.fct.order.0",
            Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw=="),
            Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA=="),
            new StringDeserializer(),
            new OrderEventOrderDeserializer()
    );

    private final String topic;
    private final String login;
    private final String password;
    private final Object keyDeserializer;
    private final Object valueDeserializer;
}
