package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class PaymentToolsV2Request {

    /**
     * Получаем список способов оплаты для юзера
     */
    @Step("{method} /" + ApiV2EndPoints.PAYMENT_TOOLS)
    public static Response GET() {
        return givenWithAuthApiV2()
                .header("Client-Id",
                        "InstamartApp")
                .header("Client-Ver",
                        "5.0")
                .get(ApiV2EndPoints.PAYMENT_TOOLS);
    }
}
