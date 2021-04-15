package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ReturnReasonsSHPRequest {

    /**
     * Получаем причины возврата
     */
    @Step("{method} /" + ShopperAppEndpoints.RETURN_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.RETURN_REASONS);
    }
}
