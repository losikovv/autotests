package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ActiveShipmentsNoAuthV2Test extends RestBase {

    @CaseId(1389)
    @Story("Текущий подзаказ")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение текущего подзаказа без авторизации")
    public void getActiveShipmentsWithoutAuth() {
        final Response response = ShipmentsV2Request.GET(null);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
