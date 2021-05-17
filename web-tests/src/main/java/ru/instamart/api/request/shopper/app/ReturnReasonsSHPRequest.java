package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class ReturnReasonsSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем причины возврата
     */
    @Step("{method} /" + ShopperAppEndpoints.RETURN_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.RETURN_REASONS);
    }
}
