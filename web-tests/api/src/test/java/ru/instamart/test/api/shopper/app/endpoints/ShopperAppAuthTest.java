package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.app.ScangoSHPRequest;
import ru.instamart.api.request.shopper.app.ShopperDriverSHPRequest;
import ru.instamart.api.request.shopper.app.ShopperSHPRequest;
import ru.instamart.api.response.shopper.app.ActiveShipmentsSHPResponse;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;


@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperAppAuthTest extends RestBase {
    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_APP, UserManager.getDefaultShopper());
    }

    @Story("Сборки/отгрузки")
    @CaseId(73)
    @Test(description = "Список активных отгрузок магазина текущего партнёра",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void activeShipments200() {
        final Response response = ShopperSHPRequest.Shipments.Active.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentsSHPResponse.class);
    }

    @Story("Сборки/отгрузки")
    @CaseId(108)
    @Test(description = "Список активных сборок/отгрузок магазина текущего партнёра для универсалов",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void shopperDriverActive200() {
        final Response response = ShopperDriverSHPRequest.Shipments.Active.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentsSHPResponse.class);
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
