package ru.instamart.kafka.configs;

import ru.instamart.kafka.KafkaConfig;
import ru.instamart.kraken.common.Crypt;

import static ru.instamart.kraken.config.EnvironmentProperties.Env.SURGELEVEL_HASH_OR_BRANCH;

public class KafkaConfigs {
    private static String workflowUser = Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw==");
    private static String workflowPass = Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA==");
    private static String retailerUser = Crypt.INSTANCE.decrypt("8umFnbBPQlFkXL2Ngw2wyMEEUcogUnQinsGC6YpsrmJOnXIBqr63D420KmvvZXtc");
    private static String retailerPass = Crypt.INSTANCE.decrypt("B0VN+R0oaWvTwe5+OBq5Nw==");

    public static KafkaConfig configFctOrderStf() {
        return KafkaConfig.builder()
                .topic("yc.operations-order-service.fct.order.0")
                .login(Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw=="))
                .password(Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA=="))
                .build();
    }

    public static KafkaConfig configCmdStatusOrderRequest() {
        return KafkaConfig.builder()
                .topic("yc.operations-order-service.cmd.status-order-request.0")
                .login(Crypt.INSTANCE.decrypt("W8t2xfaWNDbOiygPZb58sw=="))
                .password(Crypt.INSTANCE.decrypt("qDEePBkZyGVm909bb9boEA=="))
                .build();
    }

    public static KafkaConfig configCmdOrderEnrichment() {
        return KafkaConfig.builder()
                .topic("yc.operations-order-service.fct.order-enrichment.0")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lFFIilsK0XE3QDBBJ5Mp5mLg7qqzpEpt9aWM7PuwzRaD"))
                .password(Crypt.INSTANCE.decrypt("PKAvj0y9ppJkNVRO4kEYHw=="))
                .build();
    }

    public static KafkaConfig configWorkflowAssignment() {
        return KafkaConfig.builder()
                .topic("yc.workflow.fct.assignment.0")
                .login(workflowUser)
                .password(workflowPass)
                .build();
    }

    public static KafkaConfig configWorkflowExternalDelivery() {
        return KafkaConfig.builder()
                .topic("yc.workflow.cmd.external-delivery.0")
                .login(workflowUser)
                .password(workflowPass)
                .build();
    }

    public static KafkaConfig configWorkflowSegment() {
        return KafkaConfig.builder()
                .topic("yc.workflow.fct.segment.0")
                .login(workflowUser)
                .password(workflowPass)
                .build();
    }

    public static KafkaConfig configWorkflow() {
        return KafkaConfig.builder()
                .topic("yc.workflow.fct.workflow.0")
                .login(workflowUser)
                .password(workflowPass)
                .build();
    }

    public static KafkaConfig configNotifications() {
        return KafkaConfig.builder()
                .topic("yc.notifications.cmd.push.0")
                .login(workflowUser)
                .password(workflowPass)
                .build();
    }

    public static KafkaConfig configStoreChanged() {
        return KafkaConfig.builder()
                .topic("yc.operations.cdc.store-changed.06")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lMkECi+EDMxY8zgsSNyrupA="))
                .password(Crypt.INSTANCE.decrypt("bjueBoIINCB7R5RXDn/hsw=="))
                .build();
    }

    public static KafkaConfig configPlanningPeriods() {
        return KafkaConfig.builder()
                .topic("yc.shifts.cmd.planning_periods.0")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lMkECi+EDMxY8zgsSNyrupA="))
                .password(Crypt.INSTANCE.decrypt("bjueBoIINCB7R5RXDn/hsw=="))
                .build();
    }

    public static KafkaConfig configSurgeLevel() {
        return KafkaConfig.builder()
                .topic("yc.surge.fct.result.0" + SURGELEVEL_HASH_OR_BRANCH)
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lNLTflx90VI0n6hzBgecvll6tdcTqGMheErKEU2y3QOl"))
                .password(Crypt.INSTANCE.decrypt("2upy3AL136//Hs/NMMaWnw=="))
                .build();
    }

    public static KafkaConfig configCandidateStatus() {
        return KafkaConfig.builder()
                .topic("yc.operations-candidates.fct.candidate-status.0")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lNLTflx90VI0n6hzBgecvll6tdcTqGMheErKEU2y3QOl"))
                .password(Crypt.INSTANCE.decrypt("2upy3AL136//Hs/NMMaWnw=="))
                .build();
    }

    public static KafkaConfig configNorns() {
        return KafkaConfig.builder()
                .topic("paas-content-operations-norns.save-location")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lNLTflx90VI0n6hzBgecvll6tdcTqGMheErKEU2y3QOl"))
                .password(Crypt.INSTANCE.decrypt("2upy3AL136//Hs/NMMaWnw=="))
                .build();
    }

    public static KafkaConfig configOrderStatusChanged() {
        return KafkaConfig.builder()
                .topic("yc.operations-order-service.fct.order-status-changed.0")
                .login(Crypt.INSTANCE.decrypt("dIOB+Ef13KgRMN6N0cm7lNLTflx90VI0n6hzBgecvll6tdcTqGMheErKEU2y3QOl"))
                .password(Crypt.INSTANCE.decrypt("2upy3AL136//Hs/NMMaWnw=="))
                .build();
    }

    public static KafkaConfig configRetailerChanged() {
        return KafkaConfig.builder()
                .topic("yc.retail-onboarding.fct.retailers.16")
                .login(retailerUser)
                .password(retailerPass)
                .build();
    }

    public static KafkaConfig configRetailerOnboardingStoreChanged() {
        return KafkaConfig.builder()
                .topic("yc.retail-onboarding.fct.stores.16")
                .login(retailerUser)
                .password(retailerPass)
                .build();
    }
}
