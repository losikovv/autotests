package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class PaymentToolsV2Request extends ApiV2RequestBase {

    /**
     * Получаем список способов оплаты для юзера
     */
    @Step("{method} /" + ApiV2EndPoints.PAYMENT_TOOLS)
    public static Response GET() {
        return givenWithAuth()
                .header("Client-Id",
                        "InstamartApp")
                .header("Client-Ver",
                        "5.0")
                .get(ApiV2EndPoints.PAYMENT_TOOLS);
    }
}
