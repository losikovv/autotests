package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class PackerSHPRequest {

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
