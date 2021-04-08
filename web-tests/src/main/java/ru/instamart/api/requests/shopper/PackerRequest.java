package ru.instamart.api.requests.shopper;

import ru.instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class PackerRequest {

    public static class Shipments {
        /**
         * Получаем список заказов для сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Packer.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Packer.SHIPMENTS);
        }
    }
    public static class Assemblies {
        /**
         * Получаем список доставок сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Packer.ASSEMBLIES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Packer.ASSEMBLIES);
        }
    }
}
