package ru.instamart.api.requests.shopper;

import ru.instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class CancelReasonsRequest {

    /**
     * Получаем причины отмен
     */
    @Step("{method} /" + ShopperApiEndpoints.CANCEL_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.CANCEL_REASONS);
    }
}
