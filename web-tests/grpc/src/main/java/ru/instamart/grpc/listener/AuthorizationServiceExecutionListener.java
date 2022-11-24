package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;

import java.util.Map;

@Slf4j
public class AuthorizationServiceExecutionListener extends ExecutionListener {
    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public AuthorizationServiceExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        this.revealKraken();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
    }

    public void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("gRPC", GrpcContentHosts.PAAS_CONTENT_CORE_SERVICES_AUTHORIZATION)));
    }

    public void revealKraken() {
        log.debug("gRPC {}", GrpcContentHosts.PAAS_CONTENT_CORE_SERVICES_AUTHORIZATION);
        log.debug("TEST RUN ID: {}", runId);
    }
}
