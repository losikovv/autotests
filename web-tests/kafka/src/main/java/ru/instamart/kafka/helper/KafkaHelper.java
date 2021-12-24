package ru.instamart.kafka.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import order.Order;
import order_enrichment.OrderEnrichment;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kafka.consumer.KafkaConsumers;
import ru.instamart.kafka.emum.StatusOrder;
import ru.instamart.kafka.producer.KafkaProducers;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class KafkaHelper {

    @Step("Получаем данных кафки по orderUUID: {orderUUID}")
    public List<Order.EventOrder> waitDataInKafkaTopicFtcOrder(KafkaConfig config, String orderUUID, StatusOrder postponed) {
        var kafkaConsumers = new KafkaConsumers(config);
        List<Order.EventOrder> longEventOrderHashMap = kafkaConsumers.consumeEventOrder(orderUUID, postponed);
        assertTrue(longEventOrderHashMap.size()>0, "Logs is empty");
        return longEventOrderHashMap;
    }

    public List<Order.EventOrder> waitDataInKafkaTopicFtcOrder(KafkaConfig config, String orderUUID) {
        return waitDataInKafkaTopicFtcOrder(config, orderUUID, null);
    }

    @Step("Фильтр данных кафки по orderUUID")
    private List<Order.EventOrder> getOrderData(List<Order.EventOrder> list, String filter) {
        List<Order.EventOrder> collect = list.stream()
                .filter(p -> p.getOrderUuid() == filter)
                .collect(Collectors.toList());
        Allure.addAttachment("Данные по заказу", list.toString());
        return collect;
    }


    @Step("Проверка смены статуса на {postponed.value} сообщения")
    public void checkChangeStatusOrder(List<Order.EventOrder> list, StatusOrder postponed) {
        List<Order.EventOrder> collect = list.stream()
                .filter(p -> p.getShipmentStatus().getValueDescriptor().equals(postponed.getValue()))
                .collect(Collectors.toList());
        Allure.addAttachment("Data", collect.toString());
        assertTrue(collect.size() > 0, "Нет данных по смене статуса");
    }

    public void publish(KafkaConfig config, byte[] msg){
        KafkaProducers producer = new KafkaProducers();
        producer.publish(config, msg);

    }

    @Step("Получаем данных кафки по orderUUID: {shipmentUuid}")
    public List<OrderEnrichment.EventOrderEnrichment> waitDataInKafkaTopicStatusOrderRequest(KafkaConfig configCmdStatusOrderRequest, String shipmentUuid, StatusOrder automaticRouting) {
        var kafkaConsumers = new KafkaConsumers(configCmdStatusOrderRequest);
        List<OrderEnrichment.EventOrderEnrichment> longEventOrderHashMap = kafkaConsumers.consumeOrderEnrichment(shipmentUuid, automaticRouting);
        assertTrue(longEventOrderHashMap.size()>0, "Logs is empty");
        return longEventOrderHashMap;
    }
}
