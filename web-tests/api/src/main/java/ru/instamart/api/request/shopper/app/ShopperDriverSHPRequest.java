package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;
import ru.sbermarket.common.Mapper;

public class ShopperDriverSHPRequest extends ShopperAppRequestBase {

    public static class Shipments {

        public static class Active{

            @Step("{method} /" + ShopperAppEndpoints.ShopperDriver.Shipments.ACTIVE)
            public static Response GET(){
                return givenWithAuth()
                        .get(ShopperAppEndpoints.ShopperDriver.Shipments.ACTIVE);
            }
        }

    }
}
