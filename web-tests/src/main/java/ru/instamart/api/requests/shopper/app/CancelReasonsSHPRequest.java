package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

public final class CancelReasonsSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем причины отмен
     */
    @Step("{method} /" + ShopperAppEndpoints.CANCEL_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.CANCEL_REASONS);
    }
}
