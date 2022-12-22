package ru.instamart.kafkaesque.serializer;

import protobuf.order_data.OrderOuterClass;
import protobuf.order_data.OrderOuterClass.Order;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import ru.instamart.kafkaesque.Adapter;

public class OrderEventOrderSerializer extends Adapter implements Serializer<OrderOuterClass.Order> {
    @Override
    public byte[] serialize(String topic, OrderOuterClass.Order data) {
        return data.toByteArray();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, OrderOuterClass.Order data) {
        return serialize(topic, data);
    }
}
