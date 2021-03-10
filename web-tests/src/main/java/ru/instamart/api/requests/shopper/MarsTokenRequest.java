package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class MarsTokenRequest {

    /**
     * Получаем марс токен (стоки метро)
     */
    @Step("{method} /" + ShopperApiEndpoints.MARS_TOKEN)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.MARS_TOKEN);
    }
}
