package instamart.api.common;

import instamart.api.helpers.ApiV2Helper;
import instamart.api.helpers.ShopperApiHelper;
import instamart.core.helpers.ConsoleOutputCapturerHelper;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static instamart.core.helpers.HelperBase.verboseMessage;

public class RestBase {

    private static final ConsoleOutputCapturerHelper CAPTURE_HELPER = new ConsoleOutputCapturerHelper();

    protected static final ApiV2Helper apiV2 = new ApiV2Helper();
    protected static final ShopperApiHelper shopper = new ShopperApiHelper();
    protected Response response;

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

    @AfterMethod(description = "Отмена активных заказов",
                 groups = {
                    "api-zones",
                    "api-shopper-regress",
                         "MRAutoCheck"},
                 alwaysRun = true)
    public void cancelActiveOrders() {
        if (apiV2.authorized() &&
                EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            verboseMessage("Отменяем активные заказы");
            apiV2.cancelActiveOrders();
        }
    }

    @BeforeMethod(alwaysRun = true,description = "Стартуем запись системного лога")
    public void captureStart(){
        CAPTURE_HELPER.start();
    }

    @AfterMethod(alwaysRun = true,description = "Добавляем системный лог к тесту")
    public void captureFinish() {
        Allure.addAttachment("Системный лог теста",CAPTURE_HELPER.stop());
    }
}
