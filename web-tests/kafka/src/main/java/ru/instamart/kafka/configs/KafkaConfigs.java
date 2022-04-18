package ru.instamart.kafka.configs;

import ru.instamart.kafka.KafkaConfig;
import ru.sbermarket.common.Crypt;

public class KafkaConfigs {

    public static KafkaConfig configFctOrderStf() {
        return KafkaConfig.builder()
                .clientId("stf-app")
                .topic("yc.operations-order-service.fct.order.0")
                .groupName("stf_dispatch_consumer6")
                .login(Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw=="))
                .password(Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA=="))
                .build();
    }

    public static KafkaConfig configCmdStatusOrderRequest() {
        return KafkaConfig.builder()
                .clientId("stf-app")
                .topic("yc.operations-order-service.cmd.status-order-request.0")
                .groupName("stf_dispatch_consumer6")
                .login(Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw=="))
                .password(Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA=="))
                .build();
    }

    public static KafkaConfig configCmdOrderEnrichment() {
        return KafkaConfig.builder()
                .clientId("order-service")
                .topic("yc.operations-order-service.fct.order-enrichment.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lFFIilsK0XE3QDBBJ5Mp5mLg7qqzpEpt9aWM7PuwzRaD"))
                .password(Crypt.INSTANCE.decrypt("oxtATtB9KZVs9w97BTNtYg=="))
                .build();
    }

    public static KafkaConfig configWorkflowAssignment() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.assignment.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("JJVKjJO54cbtp9MUf2+/Xg=="))
                .build();
    }

    public static KafkaConfig configWorkflowExternalDelivery() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.cmd.external-delivery.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("JJVKjJO54cbtp9MUf2+/Xg=="))
                .build();
    }

    public static KafkaConfig configWorkflowSegment() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.segment.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("JJVKjJO54cbtp9MUf2+/Xg=="))
                .build();
    }

    public static KafkaConfig configWorkflow() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.workflow.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("JJVKjJO54cbtp9MUf2+/Xg=="))
                .build();
    }
}
