package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode403;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ShipmentsOtherAuthV2Test extends RestBase {

    @Skip(onServer = Server.STAGING)
    @CaseId(785)
    @Story("Получения статуса шипмента")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получения статуса шипмента с существующим shipmentNumber другого пользователя")
    public void getShipmentState403() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String number = apiV2.getShipmentsNumber();
        SessionFactory.clearSession(SessionType.API_V2);
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.State.GET(number);
        checkStatusCode403(response);
        checkError(response, "Пользователь не может выполнить это действие");
    }
}
