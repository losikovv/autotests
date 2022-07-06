package ru.instamart.api.listener.on_demand;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.listener.ApiExecutionListener;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.helper.AllureHelper;

import java.util.Map;

@Slf4j
public final class DispatchExecutionListener extends ApiExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public DispatchExecutionListener() {
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
                        Map.entry("STF", EnvironmentProperties.Env.FULL_SITE_URL),
                        Map.entry("Shopper", EnvironmentProperties.Env.FULL_SHOPPER_URL)),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    public void revealKraken() {
        log.debug("STF {}", EnvironmentProperties.Env.FULL_SITE_URL);
        log.debug("Shopper {}", EnvironmentProperties.Env.FULL_SHOPPER_URL);
        log.debug("TEST RUN ID: {}", runId);
    }
}
