package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class CancelReasonsRequest {

    /**
     * Получаем причины отмен
     */
    @Step("{method} /" + ShopperApiEndpoints.CANCEL_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.CANCEL_REASONS);
    }
}
