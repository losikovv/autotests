package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

public final class MarsTokenSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем марс токен (стоки метро)
     */
    @Step("{method} /" + ShopperAppEndpoints.MARS_TOKEN)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.MARS_TOKEN);
    }
}
