package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

public final class RoutesSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем маршруты
     */
    @Step("{method} /" + ShopperAppEndpoints.ROUTES)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.ROUTES);
    }
}
