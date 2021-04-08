package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class MarsTokenSHPRequest {

    /**
     * Получаем марс токен (стоки метро)
     */
    @Step("{method} /" + ShopperApiEndpoints.MARS_TOKEN)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.MARS_TOKEN);
    }
}
