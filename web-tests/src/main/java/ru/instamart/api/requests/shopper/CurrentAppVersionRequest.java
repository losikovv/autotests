package ru.instamart.api.requests.shopper;

import ru.instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

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
