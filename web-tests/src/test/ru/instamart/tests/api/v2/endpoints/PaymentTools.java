package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.Requests;
import instamart.api.common.RestBase;
import instamart.api.objects.responses.PaymentToolsResponse;
import instamart.core.common.AppManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PaymentTools extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        kraken.rest().authorisation(AppManager.session.admin);
    }

    @Test(  description = "Получаем инфу способах оплаты",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 20)
    public void getPaymentTools() {
        response = Requests.getPaymentTools();

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(PaymentToolsResponse.class).getPayment_tools(),
                "Не вернулась инфа о спобах оплаты");
    }
}
