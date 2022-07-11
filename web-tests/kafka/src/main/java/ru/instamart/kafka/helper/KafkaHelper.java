package ru.instamart.kafka.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Cleanup;
import order.Order;
import order_enrichment.OrderEnrichment;
import order_status.OrderStatus;
import push.Push;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kafka.consumer.KafkaConsumers;
import ru.instamart.kafka.enums.StatusOrder;
import ru.instamart.kafka.producer.KafkaProducers;
import workflow.AssignmentChangedOuterClass;
import workflow.ExternalDeliveryOuterClass;
import workflow.SegmentChangedOuterClass;
import workflow.WorkflowChangedOuterClass;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kafka.configs.KafkaConfigs.*;

public class KafkaHelper {

    @Step("Получаем данные из топика {config.topic} кафки по orderUUID: {orderUUID}")
    public List<Order.EventOrder> waitDataInKafkaTopicFtcOrder(KafkaConfig config, String orderUUID, StatusOrder postponed) {
        @Cleanup
        var kafkaConsumers = new KafkaConsumers(config, 10L);
        List<Order.EventOrder> longEventOrderHashMap = kafkaConsumers.consumeEventOrder(orderUUID, postponed);
        kafkaConsumers.close();
        assertTrue(longEventOrderHashMap.size() > 0, "Logs is empty");
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

    @Step("Отправка сообщения в топик {config.topic}")
    public void publish(KafkaConfig config, byte[] msg) {
        KafkaProducers producer = new KafkaProducers();
        producer.publish(config, msg);
        producer.shutdown();
    }

    @Step("Отправка сообщения в топик {config.topic}")
    public void publish(KafkaConfig config, List<byte[]> msgs) {
        KafkaProducers producer = new KafkaProducers();
        msgs.stream().forEach(
                msg -> producer.publish(config, msg)
        );
        producer.shutdown();
    }

    @Step("Получаем данных кафки по shipmentUUID: {shipmentUuid}")
    public List<OrderEnrichment.EventOrderEnrichment> waitDataInKafkaTopicConsumeOrderEnrichment(String shipmentUuid, StatusOrder automaticRouting) {
        var kafkaConsumers = new KafkaConsumers(configCmdOrderEnrichment(), 10L);
        var longEventOrderHashMap = kafkaConsumers.consumeOrderEnrichment(shipmentUuid, automaticRouting);
        assertTrue(longEventOrderHashMap.size() > 0, "Logs is empty");
        return longEventOrderHashMap;
    }

    @Step("Получаем данных кафки по shipmentUUID: {shipmentUuid}")
    public List<OrderStatus.EventStatusRequest> waitDataInKafkaTopicStatusOrderRequest(String shipmentUuid, StatusOrder automaticRouting) {
        var kafkaConsumers = new KafkaConsumers(configCmdStatusOrderRequest(), 10L);
        List<OrderStatus.EventStatusRequest> longEventOrderHashMap = kafkaConsumers.consumeOrderStatus(shipmentUuid, automaticRouting);
        kafkaConsumers.close();
        assertTrue(longEventOrderHashMap.size() > 0, "Logs is empty");
        return longEventOrderHashMap;
    }

    @Step("Получаем данных кафки о назначениях по uuid workflow: {uuid}")
    public List<AssignmentChangedOuterClass.AssignmentChanged> waitDataInKafkaTopicWorkflowAssignment(String uuid) {
        var kafkaConsumers = new KafkaConsumers(configWorkflowAssignment(), 10L);
        List<AssignmentChangedOuterClass.AssignmentChanged> longAssignmentsHashMap = kafkaConsumers.consumeAssignments(uuid);
        kafkaConsumers.close();
        assertTrue(longAssignmentsHashMap.size() > 0, "Logs is empty");
        return longAssignmentsHashMap;
    }

    @Step("Получаем данные кафки о внешних доставках по uuid workflow: {uuid}")
    public List<ExternalDeliveryOuterClass.ExternalDelivery> waitDataInKafkaTopicWorkflowExternalDelivery(String uuid) {
        var kafkaConsumers = new KafkaConsumers(configWorkflowExternalDelivery(), 10L);
        List<ExternalDeliveryOuterClass.ExternalDelivery> longExternalDeliveriesHashMap = kafkaConsumers.consumeExternalDeliveries(uuid);
        kafkaConsumers.close();
        assertTrue(longExternalDeliveriesHashMap.size() > 0, "Logs is empty");
        return longExternalDeliveriesHashMap;
    }

    @Step("Получаем данные кафки о сегментах маршрутного листа по id сегмента: {segmentId}")
    public List<SegmentChangedOuterClass.SegmentChanged> waitDataInKafkaTopicWorkflowSegment(long segmentId) {
        var kafkaConsumers = new KafkaConsumers(configWorkflowSegment(), 10L);
        List<SegmentChangedOuterClass.SegmentChanged> longSegmentsHashMap = kafkaConsumers.consumeSegments(segmentId);
        kafkaConsumers.close();
        assertTrue(longSegmentsHashMap.size() > 0, "Logs is empty");
        return longSegmentsHashMap;
    }

    @Step("Получаем данные кафки о маршрутных листах по id: {workflowId}")
    public List<WorkflowChangedOuterClass.WorkflowChanged> waitDataInKafkaTopicWorkflow(long workflowId) {
        var kafkaConsumers = new KafkaConsumers(configWorkflow());
        List<WorkflowChangedOuterClass.WorkflowChanged> longWorkflowsHashMap = kafkaConsumers.consumeWorkflows(workflowId);
        kafkaConsumers.close();
        assertTrue(longWorkflowsHashMap.size() > 0, "Logs is empty");
        return longWorkflowsHashMap;
    }

    @Step("Получаем данные кафки о сообщениях по id маршрутного листа: {workflowId}")
    public List<Push.EventPushNotification> waitDataInKafkaTopicNotifications(long workflowId) {
        var kafkaConsumers = new KafkaConsumers(configNotifications(), 10L);
        List<Push.EventPushNotification> longNotificationHashMap = kafkaConsumers.consumeNotifications(workflowId);
        kafkaConsumers.close();
        assertTrue(longNotificationHashMap.size() > 0, "Logs is empty");
        return longNotificationHashMap;
    }
}
