package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

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
