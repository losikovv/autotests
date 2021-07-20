package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

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

    @Step("{method} /" + ApiV2EndPoints.PAYMENT_TOOL_TYPES_WITH_ORDER_NUMBER)
    public static Response GET(String orderNumber){
        return givenWithAuth()
                .get(ApiV2EndPoints.PAYMENT_TOOL_TYPES_WITH_ORDER_NUMBER, orderNumber);
    }
}
