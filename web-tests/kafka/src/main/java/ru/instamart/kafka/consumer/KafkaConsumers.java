package ru.instamart.kafka.consumer;

import com.google.protobuf.InvalidProtocolBufferException;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import order.Order;
import order_enrichment.OrderEnrichment;
import order_status.OrderStatus;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kafka.emum.StatusOrder;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.util.ThreadUtil;
import workflow.AssignmentChangedOuterClass;
import workflow.ExternalDeliveryOuterClass;
import workflow.SegmentChangedOuterClass;
import workflow.WorkflowChangedOuterClass;

import java.time.Duration;
import java.util.*;

import static ru.instamart.kraken.util.TimeUtil.getDbDateMinusMinutes;
import static ru.instamart.kraken.util.TimeUtil.getZonedDate;

@Slf4j
public class KafkaConsumers {
    private static String saslConfigs = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    private KafkaConsumer<String, byte[]> consumer;

    public KafkaConsumers(KafkaConfig config) {
        this.consumer = createConsumer(config, null);
    }

    public KafkaConsumers(KafkaConfig config, Long time) {
        this.consumer = createConsumer(config, time);
    }

    private Properties consumerProperties(KafkaConfig config) {
        String saslConfig = String.format(saslConfigs, config.login, config.password);
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, config.clientId);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CoreProperties.KAFKA_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.groupName);
//        props.put("session.timeout.ms", "30000");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        //protobuf
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        //auth
        props.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-512");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.jaas.config", saslConfig);
        return props;
    }

    private KafkaConsumer<String, byte[]> createConsumer(final KafkaConfig config, Long time) {
        Properties props = consumerProperties(config);
        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);
        if (Objects.nonNull(time)) {
            log.info("time not null");
            TopicPartition topicPartition0 = new TopicPartition(config.topic, 0);
            consumer.assign(Collections.singletonList(topicPartition0));
            Map<TopicPartition, Long> startOffsetsMap = new HashMap<>();
            startOffsetsMap.put(topicPartition0, getDbDateMinusMinutes(time));
            Map<TopicPartition, OffsetAndTimestamp> startPartitionOffsetsMap = consumer
                    .offsetsForTimes(startOffsetsMap);
            long partition0StartOffset = 0;
            if (startOffsetsMap.get(topicPartition0) != null) {
                partition0StartOffset = startPartitionOffsetsMap.get(topicPartition0).offset();
            }

            log.info("Начальное смещение раздела:{}", partition0StartOffset);
            consumer.seek(topicPartition0, partition0StartOffset);
        } else {
            log.info("time null");
            consumer.subscribe(Collections.singleton(config.topic));
        }
        return consumer;
    }

    public List<Order.EventOrder> consumeEventOrder(String filter, StatusOrder postponed) {
        final List<Order.EventOrder> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<Order.EventOrder> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                Order.EventOrder parseEventOrder = null;
                try {
                    if (record.value() != null) {
                        parseEventOrder = Order.EventOrder.parseFrom(record.value());
                        log.debug("record: {}", parseEventOrder.toString());
                        allLogs.add(parseEventOrder);
                        if (parseEventOrder != null && parseEventOrder.getOrderUuid().equals(filter)) {
                            if (postponed != null && parseEventOrder.getShipmentStatus().name().equals(postponed.name())) {
                                result.add(parseEventOrder);
                            } else if (postponed == null) {
                                result.add(parseEventOrder);
                            }
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        consumer.close();
        log.debug("Kafka get data");
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }


    public List<OrderStatus.EventStatusRequest> consumeOrderStatus(KafkaConsumer<String, byte[]> consumer, String filter, StatusOrder postponed) {

        final int giveUp = 200;
        int noRecordsCount = 0;
        List<OrderStatus.EventStatusRequest> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(10000));
            for (ConsumerRecord<String, byte[]> record : records) {
                OrderStatus.EventStatusRequest parseOrderStatus = null;
                try {
                    if (record.value() != null) {
                        log.info("record: {}", record.value().toString());
                        parseOrderStatus = OrderStatus.EventStatusRequest.parseFrom(record.value());
                        if (parseOrderStatus != null && parseOrderStatus.getShipmentUuid().equals(filter)) {
                            if (parseOrderStatus.getStatus().equals(postponed)) {
                                result.add(parseOrderStatus);
                            }
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records");
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        Allure.addAttachment("Kafka log", result.toString());
        return result;
    }

    public List<OrderEnrichment.EventOrderEnrichment> consumeOrderEnrichment(String shipmentUuid, StatusOrder automaticRouting) {
        List<OrderEnrichment.EventOrderEnrichment> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<OrderEnrichment.EventOrderEnrichment> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(10000));
            for (ConsumerRecord<String, byte[]> record : records) {
                OrderEnrichment.EventOrderEnrichment parseEventOrder = null;
                try {
                    if (record.value() != null) {
                        parseEventOrder = OrderEnrichment.EventOrderEnrichment.parseFrom(record.value());
                        log.info("record: {}", parseEventOrder.toString());
                        allLogs.add(parseEventOrder);
                        if (parseEventOrder != null && parseEventOrder.getShipmentUuid().equals(shipmentUuid)) {
                            if (automaticRouting != null && parseEventOrder.getShipmentStatus().name().equals(automaticRouting.name())) {
                                result.add(parseEventOrder);
                            } else if (automaticRouting == null) {
                                result.add(parseEventOrder);
                            }
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }


    public List<OrderStatus.EventStatusRequest> consumeOrderStatus(String shipmentUuid, StatusOrder automaticRouting) {
        List<String> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<OrderStatus.EventStatusRequest> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                OrderStatus.EventStatusRequest parseEventOrder = null;
                try {
                    if (record.value() != null) {
                        parseEventOrder = OrderStatus.EventStatusRequest.parseFrom(record.value());
                        log.debug("record: {}", parseEventOrder.toString());
                        allLogs.add("Time: " + getZonedDate() + "\n Message: \n" + parseEventOrder.toString());
                        log.info("parseEventOrder.shipmentUuid: {}", parseEventOrder.getShipmentUuid());
                        log.info("parseEventOrder.status: {}", parseEventOrder.getStatus());

                        if (parseEventOrder != null && parseEventOrder.getShipmentUuid().equalsIgnoreCase(shipmentUuid)) {
                            if (automaticRouting != null && parseEventOrder.getStatus().name().equalsIgnoreCase(automaticRouting.name())) {
                                result.add(parseEventOrder);
                            } else if (automaticRouting.getValue() == null) {
                                result.add(parseEventOrder);
                            }
                        }

                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        consumer.close();
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }

    public List<AssignmentChangedOuterClass.AssignmentChanged> consumeAssignments(String workflowUuid) {
        List<String> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<AssignmentChangedOuterClass.AssignmentChanged> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                AssignmentChangedOuterClass.AssignmentChanged parseAssignment = null;
                try {
                    if (record.value() != null) {
                        parseAssignment = AssignmentChangedOuterClass.AssignmentChanged.parseFrom(record.value());
                        log.debug("record: {}", parseAssignment.toString());
                        allLogs.add("Time: " + getZonedDate() + "\n Message: \n" + parseAssignment);

                        if (Objects.nonNull(parseAssignment) && parseAssignment.getUuid().equals(workflowUuid)) {
                            result.add(parseAssignment);
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        consumer.close();
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }

    public List<ExternalDeliveryOuterClass.ExternalDelivery> consumeExternalDeliveries(String workflowUuid) {
        List<String> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<ExternalDeliveryOuterClass.ExternalDelivery> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                ExternalDeliveryOuterClass.ExternalDelivery parseExternalDelivery = null;
                try {
                    if (record.value() != null) {
                        parseExternalDelivery = ExternalDeliveryOuterClass.ExternalDelivery.parseFrom(record.value());
                        log.debug("record: {}", parseExternalDelivery.toString());
                        allLogs.add("Time: " + getZonedDate() + "\n Message: \n" + parseExternalDelivery);

                        if (Objects.nonNull(parseExternalDelivery) && parseExternalDelivery.getUuid().equals(workflowUuid)) {
                            result.add(parseExternalDelivery);
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        consumer.close();
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }

    public List<SegmentChangedOuterClass.SegmentChanged> consumeSegments(long segmentId) {
        List<String> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<SegmentChangedOuterClass.SegmentChanged> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                SegmentChangedOuterClass.SegmentChanged parseSegment = null;
                try {
                    if (record.value() != null) {
                        parseSegment = SegmentChangedOuterClass.SegmentChanged.parseFrom(record.value());
                        log.debug("record: {}", parseSegment.toString());
                        allLogs.add("Time: " + getZonedDate() + "\n Message: \n" + parseSegment);

                        if (Objects.nonNull(parseSegment) && parseSegment.getId() == segmentId) {
                            result.add(parseSegment);
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        consumer.close();
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }

    public List<WorkflowChangedOuterClass.WorkflowChanged> consumeWorkflows(long workflowId) {
        List<String> allLogs = new ArrayList<>();
        final int giveUp = 100;
        int noRecordsCount = 0;
        List<WorkflowChangedOuterClass.WorkflowChanged> result = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(50));
            for (ConsumerRecord<String, byte[]> record : records) {
                WorkflowChangedOuterClass.WorkflowChanged parseWorkflow = null;
                try {
                    if (record.value() != null) {
                        parseWorkflow = WorkflowChangedOuterClass.WorkflowChanged.parseFrom(record.value());
                        log.debug("record: {}", parseWorkflow.toString());
                        allLogs.add("Time: " + getZonedDate() + "\n Message: \n" + parseWorkflow);

                        if (Objects.nonNull(parseWorkflow) && parseWorkflow.getId() == workflowId) {
                            result.add(parseWorkflow);
                        }
                    }
                } catch (InvalidProtocolBufferException e) {
                    log.debug("Fail parsing kafka message. offset: {}, error: {}", record.offset(), e.getMessage());
                }
            }
            if (records.count() == 0) {
                ThreadUtil.simplyAwait(1);
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    log.debug("No records noRecordsCount: {}, giveUp: {}", noRecordsCount, giveUp);
                    break;
                } else continue;
            }
            consumer.commitAsync();
            break;
        }
        log.debug("Kafka get data");
        consumer.close();
        Allure.addAttachment("Filter logs", result.toString());
        Allure.addAttachment("All logs", allLogs.toString());
        return result;
    }

    public void close() {
        consumer.close();
    }
}
