package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuth;

public final class PaymentTools {

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
