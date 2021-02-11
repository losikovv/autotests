package instamart.api.common;

import com.google.common.collect.ImmutableMap;
import instamart.api.helpers.InstamartApiHelper;
import instamart.api.helpers.ShopperApiHelper;
import instamart.core.helpers.LogAttachmentHelper;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static instamart.core.helpers.AllureHelper.allureEnvironmentWriter;

public class RestBase {

    private static final Logger log = LoggerFactory.getLogger(RestBase.class);

    protected static final InstamartApiHelper apiV2 = new InstamartApiHelper();
    protected static final ShopperApiHelper shopper = new ShopperApiHelper();
    protected Response response;

    @BeforeSuite(groups = {
            "api-zones",
            "api-v2-smoke",
            "api-shopper-smoke",
            "api-v2-regress",
            "api-shopper-regress",
            "MRAutoCheck"},
            description = "Инициализация")
    public void start() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Tenant", EnvironmentData.INSTANCE.getTenant())
                        .put("URL", EnvironmentData.INSTANCE.getBasicUrl())
                        .put("Administration", EnvironmentData.INSTANCE.getAdminUrl())
                        .put("Shopper", EnvironmentData.INSTANCE.getShopperUrl())
                        .build(), System.getProperty("user.dir")
                        + "/build/allure-results/");
    }

    @BeforeClass(alwaysRun = true,
                 groups = {
                         "api-zones",
                         "api-v2-smoke",
                         "api-shopper-smoke",
                         "api-v2-regress",
                         "api-shopper-regress",
                         "MRAutoCheck"},
                 description = "Логаут")
    public void logout() {
        apiV2.logout();
    }

    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart(){
        LogAttachmentHelper.start();
    }

    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        final String result = LogAttachmentHelper.getContent();
        LogAttachmentHelper.stop();
        Allure.addAttachment("Системный лог теста", result);
    }
}
