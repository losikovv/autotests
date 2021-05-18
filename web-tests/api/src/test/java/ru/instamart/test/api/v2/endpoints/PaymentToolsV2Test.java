package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.request.v2.PaymentToolsV2Request;
import ru.instamart.api.response.v2.PaymentToolsV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Способы оплаты")
public class PaymentToolsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(20)
    @Test(  description = "Получаем инфу способах оплаты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getPaymentTools() {
        response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(PaymentToolsV2Response.class).getPaymentTools(),
                "Не вернулась инфа о спобах оплаты");
    }
}
