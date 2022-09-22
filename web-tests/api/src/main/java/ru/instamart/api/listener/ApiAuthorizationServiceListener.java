package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.helper.AllureHelper;

import java.util.Map;

@Slf4j
public class ApiAuthorizationServiceListener extends ApiExecutionListener {
    // Нужно инициализировать в конструкторе, чтобы гарантировать наличие конфигов до запуска чего либо
    public ApiAuthorizationServiceListener() {
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
                        Map.entry("Host", "http://base-app.paas-content-core-services-authorization")),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    @Override
    public void revealKraken() {
        log.debug("Host: {}", "http://base-app.paas-content-core-services-authorization");
        log.debug("TEST RUN ID: {}", runId);
    }
}
