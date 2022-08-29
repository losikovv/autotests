package ru.instamart.api.listener.on_demand;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.listener.ApiExecutionListener;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.helper.AllureHelper;

import java.util.Map;

@Slf4j
public class SurgeLevelExecutionListener extends ApiExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public SurgeLevelExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
    }

    public void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("gRPC", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL)),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    public void revealKraken() {
        log.debug("gRPC {}", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SURGELEVEL);
        log.debug("TEST RUN ID: {}", runId);
    }
}
