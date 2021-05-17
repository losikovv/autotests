package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public final class PackerSHPRequest extends ShopperAppRequestBase {

    public static class Shipments {
        /**
         * Получаем список заказов для сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Packer.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Packer.SHIPMENTS);
        }
    }
    public static class Assemblies {
        /**
         * Получаем список доставок сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Packer.ASSEMBLIES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Packer.ASSEMBLIES);
        }
    }
}
