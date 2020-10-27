package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.PaymentToolsResponse;
import instamart.core.common.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class PaymentTools extends RestBase {

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        kraken.apiV2().authorisation(AppManager.session.admin);
    }

    @Test(  description = "Получаем инфу способах оплаты",
            groups = {"rest-smoke","rest-v2-smoke"})
    public void getPaymentTools() {
        response = ApiV2Requests.getPaymentTools();
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(PaymentToolsResponse.class).getPayment_tools(),
                "Не вернулась инфа о спобах оплаты");
    }
}
