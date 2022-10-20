package ru.instamart.kafkaesque.deserializer;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import order.Order;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import ru.instamart.kafkaesque.Adapter;

@Slf4j
public class OrderEventOrderDeserializer extends Adapter implements Deserializer<Order.EventOrder> {
    @Override
    public Order.EventOrder deserialize(final String topic, byte[] data) {
        try {
            return Order.EventOrder.parseFrom(data);
        } catch (final InvalidProtocolBufferException e) {
            log.error("Received unparseable message", e);
            throw new RuntimeException("Received unparseable message " + e.getMessage(), e);
        }
    }

    @Override
    public Order.EventOrder deserialize(final String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }
}
