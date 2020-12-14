package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.PaymentToolsResponse;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class PaymentTools extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        if (!apiV2.authorized()) {
            final UserData user = Users.apiUser();
            apiV2.registration(user);
            apiV2.authorisation(user);
        }
    }

    @CaseId(20)
    @Test(  description = "Получаем инфу способах оплаты",
            groups = {"api-v2-smoke"})
    public void getPaymentTools() {
        response = ApiV2Requests.PaymentTools.GET();
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(PaymentToolsResponse.class).getPayment_tools(),
                "Не вернулась инфа о спобах оплаты");
    }
}
