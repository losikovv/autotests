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

import java.util.Map;

@Slf4j
public abstract class ExecutionListener implements IExecutionListener {

    public static final String runId = Generate.testRunId();

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

        switch (EnvironmentData.INSTANCE.getServer()) {
            case "preprod":
                System.setProperty("ALLURE_POSTFIX", "preprod");
                break;
            case "production":
                System.setProperty("ALLURE_POSTFIX", "production");
                break;
            default:
                System.setProperty("ALLURE_POSTFIX", "staging");
                break;
        }
    }

    private void revealKraken() {
        log.info("ENVIRONMENT: {} ({})", EnvironmentData.INSTANCE.getName(), EnvironmentData.INSTANCE.getBasicUrl());
        log.info("TEST RUN ID: {}", runId);
        log.info("ADMIN: {} / {}", UserManager.getDefaultAdmin().getLogin(), UserManager.getDefaultAdmin().getPassword());
        log.info("USER: {} / {}", UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
    }
}
