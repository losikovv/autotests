package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.shopper.app.ScangoSHPRequest;
import ru.instamart.api.request.shopper.app.ShopperSHPRequest;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode403;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;


@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperAppAuthTest extends RestBase {
    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
    }

    @Story("Notifications")
    @CaseId(107)
    @Test(  description = "Отметка о прочтении уведомления без авторизации PATCH",
            groups = {"api-shopper-regress"})
    public void getShopperNotificationsPatch403 () {
        String id = "2280";
        response = ShopperSHPRequest.Notifications.PATCH(id);
        checkStatusCode403(response);
    }
    @Story("Notifications")
    @CaseId(107)
    @Test(  description = "Отметка о прочтении уведомления без авторизации PUT",
            groups = {"api-shopper-regress"})
    public void getShopperNotificationsPut403 () {
        String id = "2280";
        response = ShopperSHPRequest.Notifications.PUT(id);
        checkStatusCode403(response);
    }

    @Story("ScanGo")
    @CaseId(4)
    @Test(description = "Запрос конфигурации ScanGo",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void scanGoAssemblies404(){
        final Response response = ScangoSHPRequest.Assemblies.GET("failedNumber");
        checkStatusCode404(response);
    }
}
