package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

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
