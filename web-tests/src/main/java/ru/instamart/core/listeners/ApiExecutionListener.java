package instamart.core.listeners;

import com.google.common.collect.ImmutableMap;
import instamart.ui.common.pagesdata.EnvironmentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;

public final class ApiExecutionListener extends AbstractExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ApiExecutionListener.class);

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();

        logger.info("Start allure environment writer");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Tenant", EnvironmentData.INSTANCE.getTenant())
                        .put("URL", EnvironmentData.INSTANCE.getBasicUrl())
                        .put("Administration", EnvironmentData.INSTANCE.getAdminUrl())
                        .put("Shopper", EnvironmentData.INSTANCE.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }
}
