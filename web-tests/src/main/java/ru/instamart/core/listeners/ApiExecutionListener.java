package instamart.core.listeners;

import com.google.common.collect.ImmutableMap;
import instamart.api.common.Specification;
import instamart.core.settings.Config;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IExecutionListener;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;
import static instamart.core.settings.Config.LOG;

public final class ApiExecutionListener implements IExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ApiExecutionListener.class);

    @Override
    public void onExecutionStart() {
        logger.info("Load config");
        Config.load();

        logger.info("Load environment config");
        EnvironmentData.INSTANCE.load();
        logger.info(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());

        if (LOG) {
            logger.info("Configure system out to slf4j");
            SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        }

        logger.info("Start allure environment writer");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Tenant", EnvironmentData.INSTANCE.getTenant())
                        .put("URL", EnvironmentData.INSTANCE.getBasicUrl())
                        .put("Administration", EnvironmentData.INSTANCE.getAdminUrl())
                        .put("Shopper", EnvironmentData.INSTANCE.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");

        logger.info("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {

    }
}
