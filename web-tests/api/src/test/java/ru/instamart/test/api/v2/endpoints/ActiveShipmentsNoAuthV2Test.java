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
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class ActiveShipmentsNoAuthV2Test extends RestBase {

    @CaseId(1389)
    @Story("Текущий подзаказ")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Получение текущего подзаказа без авторизации")
    public void getActiveShipmentsWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.GET(null);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
