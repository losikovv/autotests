package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;

import java.util.Map;

@Slf4j
public final class TagManagerExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public TagManagerExecutionListener() {
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

    @Override
    public void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("gRPC", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER)),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    @Override
    public void revealKraken() {
        log.debug("gRPC {}", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_TAG_MANAGER);
        log.debug("TEST RUN ID: {}", runId);
    }
}
