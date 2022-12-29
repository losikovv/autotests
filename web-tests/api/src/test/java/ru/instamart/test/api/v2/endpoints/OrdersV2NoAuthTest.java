package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.OrdersV2Request;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Заказы (orders)")
public class OrdersV2NoAuthTest extends RestBase {

    @TmsLink("1419")
    @Story("Получение заказов")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получаем заказы без авторизации")
    public void getOrders401() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = OrdersV2Request.GET();
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @TmsLink("2132")
    @Story("Получение заказов")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получаем заказы без авторизации")
    public void putOrders404() {
        final Response response = OrdersV2Request.PUT(1, "", "", 1, 0, 0, 0, "");
        checkStatusCode404(response);
    }
}
