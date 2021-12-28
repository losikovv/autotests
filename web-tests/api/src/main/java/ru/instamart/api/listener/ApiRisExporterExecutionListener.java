package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.helper.AllureHelper;

import java.util.Map;

@Slf4j
public final class ApiRisExporterExecutionListener extends ApiExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ApiRisExporterExecutionListener() {
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
                        Map.entry("Host", "https://api-deliveryclub.sbermarket.ru")), //todo убрать хардкод как появится стэйдж
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    @Override
    public void revealKraken() {
        log.debug("Host: {}", "https://api-deliveryclub.sbermarket.ru"); //todo убрать хардкод как появится стэйдж
        log.debug("TEST RUN ID: {}", runId);
    }
}
