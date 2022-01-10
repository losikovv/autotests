package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.app.ShopperSHPRequest;
import ru.instamart.api.response.shopper.app.ActiveShipmentsSHPResponse;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;


@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperAppAuthTest extends RestBase {
    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.SHOPPER_APP, UserManager.getDefaultShopper());
    }

    @Test(description = "Список активных отгрузок магазина текущего партнёра",
            groups = {"api-shopper-regress"})
    public void activeShipments200() {
        final Response response = ShopperSHPRequest.Shipments.Active.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentsSHPResponse.class);
    }

    @Test(description = "Список активных тгрузок магазина текущего партнёра",
            groups = {"api-shopper-regress"})
    public void ordersImports200() {
        final Response response = ShopperSHPRequest.Shipments.Active.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ActiveShipmentsSHPResponse.class);
    }
}
