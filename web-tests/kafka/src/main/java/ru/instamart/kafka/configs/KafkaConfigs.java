package ru.instamart.kafka.configs;

import ru.instamart.kafka.KafkaConfig;

public class KafkaConfigs {

    public static KafkaConfig configFctOrder() {
        return KafkaConfig.builder()
                .clientId("stf-app")
                .topic("yc.operations-order-service.fct.order.0")
                .groupName("stf_dispatch_consumer6")
                .login("paas-content-operations-order-service")
                .password("8RIuP7Gc4s")
                .build();
    }
    public static KafkaConfig configCmdStatusOrderRequest() {
        return KafkaConfig.builder()
                .clientId("stf-app")
                .topic("yc.operations-order-service.cmd.status-order-request.0")
                .groupName("stf_dispatch_consumer6")
                .login("paas-content-operations-order-service")
                .password("8RIuP7Gc4s")
                .build();
    }
    public static KafkaConfig configCmdOrderEnrichment() {
        return KafkaConfig.builder()
                .clientId("stf-app")
                .topic("yc.operations-order-service.fct.order-enrichment.0")
                .groupName("dispatch_2b4a0c6a")
                .login("paas-content-operations-order-service")
                .password("8RIuP7Gc4s")
                .build();
    }
}
