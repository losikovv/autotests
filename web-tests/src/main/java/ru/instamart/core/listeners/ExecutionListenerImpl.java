package instamart.core.listeners;

import com.google.common.collect.ImmutableMap;
import instamart.api.SessionFactory;
import instamart.api.common.Specification;
import instamart.core.service.BannerService;
import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.core.util.Crypt;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;

public final class ExecutionListenerImpl implements IExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionListenerImpl.class);

    public static final String runId = Generate.testRunId();

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ExecutionListenerImpl() {
        logger.info("Load config");
        Config.load();
        logger.info("Init Crypt");
        Crypt.INSTANCE.init();
    }

    @Override
    public void onExecutionStart() {
        logger.info("Load KRAKEN");
        BannerService.printBanner();

        logger.info("Load environment config");
        EnvironmentData.INSTANCE.load();

        logger.info("Init rest specification");
        Specification.INSTANCE.initSpec();

        logger.info("Setup Allure report");
        setupAllureReport();
        revealKraken();
    }

    private void setupAllureReport() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Tenant", EnvironmentData.INSTANCE.getTenant())
                        .put("URL", EnvironmentData.INSTANCE.getBasicUrl())
                        .put("Administration", EnvironmentData.INSTANCE.getAdminUrl())
                        .put("Shopper", EnvironmentData.INSTANCE.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    private void revealKraken() {
        logger.info("ENVIRONMENT: {} ({})", EnvironmentData.INSTANCE.getName(), EnvironmentData.INSTANCE.getBasicUrl());
        logger.info("TEST RUN ID: {}", runId);
        logger.info("ADMIN: {} / {}", UserManager.getDefaultAdmin().getLogin(), UserManager.getDefaultAdmin().getPassword());
        logger.info("USER: {} / {}", UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
    }

    @Override
    public void onExecutionFinish() {
        // Тут может быть код для очистки окружения после прогона тестов
        logger.info("We create {} new users", UserManager.getUserDataList().size());
        logger.info("We have {} open sessions", SessionFactory.getSessionMap().size());
    }
}
