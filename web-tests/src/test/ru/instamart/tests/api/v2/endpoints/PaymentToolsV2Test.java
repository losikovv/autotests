package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.requests.v2.PaymentToolsRequest;
import instamart.api.responses.v2.PaymentToolsResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;

public class PaymentToolsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(20)
    @Test(  description = "Получаем инфу способах оплаты",
            groups = {"api-instamart-smoke"})
    public void getPaymentTools() {
        response = PaymentToolsRequest.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(PaymentToolsResponse.class).getPaymentTools(),
                "Не вернулась инфа о спобах оплаты");
    }
}
