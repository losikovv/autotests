package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

@SuppressWarnings("unchecked")
public class OrderSHPRequest extends ShopperAppRequestBase {

    public static class Import{

        @Step("{method} /" + ShopperAppEndpoints.Orders.IMPORTS)
        public static Response POST(String orderNumber){
            return givenWithAuth()
                    .post(ShopperAppEndpoints.Orders.IMPORTS, orderNumber);
        }
    }
}
