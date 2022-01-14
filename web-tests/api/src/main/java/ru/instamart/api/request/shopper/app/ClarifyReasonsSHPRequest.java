package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class ClarifyReasonsSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем причины несоответствия
     */
    @Step("{method} /" + ShopperAppEndpoints.CLARIFY_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.CLARIFY_REASONS);
    }
}
