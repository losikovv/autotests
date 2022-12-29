package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PaymentToolsWithTypesV2Request;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV2")
@Feature("Способы оплаты")
public class PaymentToolsWithoutAuthV2Test extends RestBase {

    @TmsLink("2217")
    @Story("Способы оплаты")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2"},
            description = "Без авторизации")
    public void getPaymentToolsWithTypes401() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = PaymentToolsWithTypesV2Request.GET(null, "failedOrderId");
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
