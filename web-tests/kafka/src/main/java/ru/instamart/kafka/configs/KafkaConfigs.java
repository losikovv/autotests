package ru.instamart.kafka.configs;

import order.Order;
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
                .password(Crypt.INSTANCE.decrypt("PKAvj0y9ppJkNVRO4kEYHw=="))
                .build();
    }

    public static KafkaConfig configWorkflowAssignment() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.assignment.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configWorkflowExternalDelivery() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.cmd.external-delivery.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configWorkflowSegment() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.segment.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configWorkflow() {
        return KafkaConfig.builder()
                .clientId("workflow")
                .topic("yc.workflow.fct.workflow.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configNotifications() {
        return KafkaConfig.builder()
                .clientId("notifications")
                .topic("yc.notifications.cmd.push.0")
                .groupName("dispatch_2b4a0c6a")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configStoreChanged() {
        return KafkaConfig.builder()
                .clientId("shifts")
                .topic("yc.operations.cdc.store-changed.0")
                .groupName("paas-content-operations-shifts-874d2e41-874d2e41")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configPlanningPeriods() {
        return KafkaConfig.builder()
                .clientId("shifts")
                .topic("yc.shifts.cmd.planning_periods.0")
                .groupName("paas-content-operations-shifts-874d2e41-874d2e41")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lMkECi+EDMxY8zgsSNyrupA="))
                .password(Crypt.INSTANCE.decrypt("o/PIVwB3B/gWRDI86IdONw=="))
                .build();
    }

    public static KafkaConfig configCandidatesService(){
        return KafkaConfig.builder()
                .clientId("app")
                .topic("yc.shifts.cmd.planning_periods.0")
                .groupName("paas-content-operations-shifts-874d2e41-874d2e41")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lJSSvKqtRSJptikUG4rvY3LCR8s9+J8irzGI1mDV44r6"))
                .password(Crypt.INSTANCE.decrypt("KHB/9bQkCFnzOfzl8kW7zQ=="))
                .build();
    }

    public static KafkaConfig configSurgeLevel(){
        return KafkaConfig.builder()
                .clientId("surgelevel")
                .topic("yc.surge.fct.result.0")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lNLTflx90VI0n6hzBgecvll6tdcTqGMheErKEU2y3QOl"))
                .password(Crypt.INSTANCE.decrypt("2lS1gYSsa7AkjKFObDuh0Q=="))
                .build();
    }
}
