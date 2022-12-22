package ru.instamart.kafkaesque.deserializer;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import protobuf.order_data.OrderOuterClass;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import ru.instamart.kafkaesque.Adapter;

@Slf4j
public class OrderEventOrderDeserializer extends Adapter implements Deserializer<OrderOuterClass.Order> {
    @Override
    public OrderOuterClass.Order deserialize(final String topic, byte[] data) {
        try {
            return OrderOuterClass.Order.parseFrom(data);
        } catch (final InvalidProtocolBufferException e) {
            log.error("Received unparseable message", e);
            throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
        }
    }

    @Override
    public OrderOuterClass.Order deserialize(final String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }
}
