package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.requests.v2.PaymentToolsV2Request;
import ru.instamart.api.responses.v2.PaymentToolsV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Способы оплаты")
public class PaymentToolsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
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
