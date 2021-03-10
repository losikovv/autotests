package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ClarifyReasonsRequest {

    /**
     * Получаем причины несоответсвия
     */
    @Step("{method} /" + ShopperApiEndpoints.CLARIFY_REASONS)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.CLARIFY_REASONS);
    }
}
