package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;

import java.util.Map;

@Slf4j
public final class GrpcContentExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public GrpcContentExecutionListener() {
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
        log.debug("We create {} new users", UserManager.userDataCount());
    }

    @Override
    public void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("Product Hub Back Host", GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_BACK),
                        Map.entry("Product Hub Front Host", GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT),
                        Map.entry("Product Filter Host", GrpcContentHosts.PAAS_CONTENT_PRODUCT_FILTER),
                        Map.entry("Catalog Host", GrpcContentHosts.PAAS_CONTENT_CATALOG),
                        Map.entry("Catalog Navigation Host", GrpcContentHosts.PAAS_CONTENT_CATALOG_NAVIGATION),
                        Map.entry("Catalog Shelf Host", GrpcContentHosts.PAAS_CONTENT_CATALOG_SHELF)),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    @Override
    public void revealKraken() {
        log.debug("Product Hub Back Host: {}", GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_BACK);
        log.debug("Product Hub Front Host: {}", GrpcContentHosts.PAAS_CONTENT_PRODUCT_HUB_FRONT);
        log.debug("Product Filter Host: {}", GrpcContentHosts.PAAS_CONTENT_PRODUCT_FILTER);
        log.debug("Catalog Host: {}", GrpcContentHosts.PAAS_CONTENT_CATALOG);
        log.debug("Catalog Navigation Host: {}", GrpcContentHosts.PAAS_CONTENT_CATALOG_NAVIGATION);
        log.debug("Catalog Shelf Host: {}", GrpcContentHosts.PAAS_CONTENT_CATALOG_SHELF);
        log.debug("TEST RUN ID: {}", runId);
    }
}
