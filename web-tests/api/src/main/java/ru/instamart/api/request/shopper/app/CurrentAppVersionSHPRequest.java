package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class CurrentAppVersionSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем инфу о текущей версии приложения
     */
    @Step("{method} /" + ShopperAppEndpoints.CURRENT_APP_VERSION)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.CURRENT_APP_VERSION);
    }
}
