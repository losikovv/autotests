package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public class NextUncompletedRouteSHPRequest extends ShopperAppRequestBase {
    @Step("{method} /" + ShopperAppEndpoints.NEXT_UNCOMPLETED_ROUTE)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.NEXT_UNCOMPLETED_ROUTE);
    }
}
