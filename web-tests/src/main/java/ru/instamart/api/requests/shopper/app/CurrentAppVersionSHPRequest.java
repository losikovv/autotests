package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class CurrentAppVersionSHPRequest {

    /**
     * Получаем инфу о текущей версии приложения
     */
    @Step("{method} /" + ShopperAppEndpoints.CURRENT_APP_VERSION)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.CURRENT_APP_VERSION);
    }
}
