package ru.instamart.kraken.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.service.BannerService;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.util.Crypt;
import ru.instamart.kraken.util.ThreadUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class ExecutionListener implements IExecutionListener {

    public static final String runId = Generate.testRunId();
    private static final long start = System.nanoTime();

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ExecutionListener() {
        log.info("Load config");
        Config.load();
        log.info("Init Crypt");
        Crypt.INSTANCE.init();
    }

    @Override
    public void onExecutionStart() {
        log.info("Load KRAKEN");
        BannerService.printBanner();

        log.info("Load environment config");
        EnvironmentData.INSTANCE.load();

        log.info("Setup Allure report");
        setupAllureReport();
        revealKraken();
    }

    private void setupAllureReport() {
        AllureHelper.allureEnvironmentWriter(
                Map.ofEntries(
                        Map.entry("Tenant", EnvironmentData.INSTANCE.getTenant()),
                        Map.entry("URL", EnvironmentData.INSTANCE.getBasicUrl()),
                        Map.entry("Administration", EnvironmentData.INSTANCE.getAdminUrl()),
                        Map.entry("Shopper", EnvironmentData.INSTANCE.getShopperUrl())),
                System.getProperty("user.dir") + "/build/allure-results/");
    }

    private void revealKraken() {
        log.info("ENVIRONMENT: {} ({})", EnvironmentData.INSTANCE.getName(), EnvironmentData.INSTANCE.getBasicUrl());
        log.info("TEST RUN ID: {}", runId);
        log.info("ADMIN: {} / {}", UserManager.getDefaultAdmin().getEmail(), UserManager.getDefaultAdmin().getPassword());
        log.info("USER: {} / {}", UserManager.getDefaultUser().getEmail(), UserManager.getDefaultUser().getPassword());
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
