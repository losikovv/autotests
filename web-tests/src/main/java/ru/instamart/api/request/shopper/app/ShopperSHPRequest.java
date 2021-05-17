package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

@SuppressWarnings("unchecked")
public final class ShopperSHPRequest extends ShopperAppRequestBase {

    /**
     * Получаем инфу о шоппере
     */
    @Step("{method} /" + ShopperAppEndpoints.SHOPPER)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperAppEndpoints.SHOPPER);
    }

    public static class Shipments {
        /**
         * Получаем список заказов для сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Shopper.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Shopper.SHIPMENTS);
        }
    }
    public static class Assemblies {
        /**
         * Получаем список доставок сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Shopper.ASSEMBLIES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Shopper.ASSEMBLIES);
        }
    }
    public static class OperationShifts {
        /**
         * Получаем список смен сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Shopper.OPERATION_SHIFTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Shopper.OPERATION_SHIFTS);
        }
        /**
         * Создаём смену для сборщика
         */
        @Step("{method} /" + ShopperAppEndpoints.Shopper.OPERATION_SHIFTS)
        public static Response POST(
                int roleId,
                boolean started,
                String planStartsAt,
                String planEndsAt,
                double lat,
                double lon) {
            JSONObject requestParams = new JSONObject();
            JSONObject operationShift = new JSONObject();
            JSONObject location = new JSONObject();
            requestParams.put("operation_shift", operationShift);
            requestParams.put("location", location);
            operationShift.put("role_id", roleId);
            operationShift.put("started", started);
            operationShift.put("plan_starts_at", planStartsAt);
            operationShift.put("plan_ends_at", planEndsAt);
            location.put("latitude", lat);
            location.put("longitude", lon);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.Shopper.OPERATION_SHIFTS);
        }
    }
}
