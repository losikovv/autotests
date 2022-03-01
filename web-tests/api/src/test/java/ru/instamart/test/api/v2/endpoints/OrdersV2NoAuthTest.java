package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.OrdersV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Заказы (orders)")
public class OrdersV2NoAuthTest extends RestBase {

    @CaseId(1419)
    @Story("Получение заказов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получаем заказы без авторизации")
    public void getOrders401() {
        final Response response = OrdersV2Request.GET();
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(1419)
    @Story("Получение заказов")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получаем заказы без авторизации")
    public void putOrders404() {
        final Response response = OrdersV2Request.PUT(1, "", "", 1, 0, 0, 0, "");
        checkStatusCode404(response);
    }
}
