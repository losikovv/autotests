package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class CurrentAppVersionRequest {

    /**
     * Получаем инфу о текущей версии приложения
     */
    @Step("{method} /" + ShopperApiEndpoints.CURRENT_APP_VERSION)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.CURRENT_APP_VERSION);
    }
}
