package ru.instamart.api.listener.on_demand;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.listener.ApiExecutionListener;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.helper.AllureHelper;

import java.util.Map;

@Slf4j
public final class RouteEstimatorExecutionListener extends ApiExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public RouteEstimatorExecutionListener() {
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
                        Map.entry("gRPC", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR)));
    }

    public void revealKraken() {
        log.debug("gRPC {}", GrpcContentHosts.PAAS_CONTENT_OPERATIONS_ROUTE_ESTIMATOR);
        log.debug("TEST RUN ID: {}", runId);
    }
}
