package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ClarifyReasonsSHPRequest {

    /**
     * Получаем причины несоответсвия
     */
    @Step("{method} /" + ShopperApiEndpoints.CLARIFY_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.CLARIFY_REASONS);
    }
}
