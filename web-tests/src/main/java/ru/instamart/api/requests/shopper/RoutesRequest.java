package ru.instamart.api.requests.shopper;

import ru.instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class RoutesRequest {

    /**
     * Получаем маршруты
     */
    @Step("{method} /" + ShopperApiEndpoints.ROUTES)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.ROUTES);
    }
}
