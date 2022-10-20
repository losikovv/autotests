package ru.instamart.kafkaesque.serializer;

import order.Order;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import ru.instamart.kafkaesque.Adapter;

public class OrderEventOrderSerializer extends Adapter implements Serializer<Order.EventOrder> {
    @Override
    public byte[] serialize(String topic, Order.EventOrder data) {
        return data.toByteArray();
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Order.EventOrder data) {
        return serialize(topic, data);
    }
}
