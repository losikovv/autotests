package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ShopperRequest {

    /**
     * Получаем инфу о шоппере
     */
    @Step("{method} /" + ShopperApiEndpoints.SHOPPER)
    public static Response GET() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.SHOPPER);
    }

    public static class Shipments {
        /**
         * Получаем список заказов для сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Shopper.SHIPMENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shopper.SHIPMENTS);
        }
    }
    public static class Assemblies {
        /**
         * Получаем список доставок сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Shopper.ASSEMBLIES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shopper.ASSEMBLIES);
        }
    }
    public static class OperationShifts {
        /**
         * Получаем список смен сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Shopper.OPERATION_SHIFTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shopper.OPERATION_SHIFTS);
        }
        /**
         * Создаём смену для сборщика
         */
        @Step("{method} /" + ShopperApiEndpoints.Shopper.OPERATION_SHIFTS)
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
                    .post(ShopperApiEndpoints.Shopper.OPERATION_SHIFTS);
        }
    }
}
