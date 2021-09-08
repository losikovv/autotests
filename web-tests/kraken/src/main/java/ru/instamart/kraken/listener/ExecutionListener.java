package ru.instamart.kraken.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import ru.instamart.kraken.config.ConfigManager;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.service.BannerService;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.utils.Crypt;

import java.util.Map;

@Slf4j
public abstract class ExecutionListener implements IExecutionListener {

    public static final String runId = Generate.testRunId();
    private static final long start = System.nanoTime();

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ExecutionListener() {
        log.info("Init Crypt");
        Crypt.INSTANCE.init();
        log.info("Load Core config");
        ConfigManager.getInstance().loadConfig();
    }

    @Override
    public void onExecutionStart() {
        log.info("Load KRAKEN");
        BannerService.printBanner();

        log.info("Setup Allure report");
        setupAllureReport();
        revealKraken();
    }

    private void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("Tenant", EnvironmentProperties.Env.ENV_NAME),
                        Map.entry("URL", EnvironmentProperties.Env.FULL_SITE_URL),
                        Map.entry("Administration", EnvironmentProperties.Env.FULL_ADMIN_URL),
                        Map.entry("Shopper", EnvironmentProperties.Env.FULL_SHOPPER_URL)),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    private void revealKraken() {
        log.info("ENVIRONMENT: {} ({})", EnvironmentProperties.TENANT, EnvironmentProperties.Env.FULL_SITE_URL);
        log.info("Tenant {}", EnvironmentProperties.Env.ENV_NAME);
        log.info("URL {}", EnvironmentProperties.Env.FULL_SITE_URL);
        log.info("Administration {}", EnvironmentProperties.Env.FULL_ADMIN_URL);
        log.info("Shopper {}", EnvironmentProperties.Env.FULL_SHOPPER_URL);
        log.info("TEST RUN ID: {}", runId);
    }

    @Override
    public void onExecutionFinish() {
        log.info("We wait {} seconds", ThreadUtil.ALL_WAIT_TIME.doubleValue());
        log.info("All tests finished for {}", getReadableTime(System.nanoTime() - start));
    }

    private String getReadableTime(final Long nanos){
        final long tempSec = nanos / (1000 * 1000 * 1000);
        final long sec = tempSec % 60;
        final long min = (tempSec /60) % 60;
        return String.format("%dm %ds", min, sec);
    }
}
