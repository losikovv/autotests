package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

import java.util.UUID;

public class OrderV3Request extends ApiV3RequestBase {
    /**
     *  Получение ордера по UUID
     */
    @Step("{method} /" + ApiV3Endpoints.Orders.ORDER_BY_UUID)
    public static Response GET(String UUID)
    {
        return givenWithSpec()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Api-Version","3.0")
                .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                .get(ApiV3Endpoints.Orders.ORDER_BY_UUID,UUID);
    }

    public static class PickupFromStore {
        /**
         * Создание заказа замовывоз
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.PICKUP_FROM_STORE)
        public static Response POST(String paymentOptionId,
                                    String shippingOptionId,
                                    String replacementOptionId,
                                    String itemId,
                                    String clientToken,
                                    String shipTotal,
                                    int quantity,
                                    int price,
                                    int discount
        ) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("number", UUID.randomUUID());
            requestParams.put("ship_total", shipTotal);
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", itemId);
            itemParams.put("quantity", quantity);
            itemParams.put("price", price);
            itemParams.put("discount", discount);
            JSONObject contactParams = new JSONObject();
            requestParams.put("contact", contactParams);
            contactParams.put("first_name","Qasper");
            contactParams.put("last_name","Tester");
            contactParams.put("email","Qasper.Tester@yandex.ru");
            contactParams.put("phone","79268202951");
            JSONObject shippingParams = new JSONObject();
            requestParams.put("shipping", shippingParams);
            shippingParams.put("option_id", shippingOptionId);
            JSONObject paymentParams = new JSONObject();
            requestParams.put("payment", paymentParams);
            paymentParams.put( "option_id", paymentOptionId);
            JSONObject replacementParams = new JSONObject();
            requestParams.put("replacement", replacementParams);
            replacementParams.put( "option_id", replacementOptionId);
            return givenWithSpec()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token",clientToken)
                    .post(ApiV3Endpoints.Orders.PICKUP_FROM_STORE);
        }
    }

    public static class Cancel {
        /**
         *  Отмена заказа
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.CANCEL)
        public static Response PUT(String UUID)
        {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status","canceled");
            return givenWithSpec()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .body(requestParams)
                    .put(ApiV3Endpoints.Orders.CANCEL,UUID);
        }
    }

    public static class Delivery {
        /**
         * Создание заказа на доставку
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.DELIVERY)
        public static Response POST(String paymentOptionId, String shippingOptionId, String replacementOptionId) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("number", UUID.randomUUID());
            //requestParams.put("ship_total", "15000");
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", "15879");
            itemParams.put("quantity", 10);
            itemParams.put("price", 1111);
            itemParams.put("discount", 0);
            itemParams.put("promo_total", 10);
            JSONObject contactParams = new JSONObject();
            requestParams.put("contact", contactParams);
            contactParams.put("first_name", "Qasper");
            contactParams.put("last_name", "Tester");
            contactParams.put("email", "Qasper.Tester@yandex.ru");
            contactParams.put("phone", "79268202951");
            JSONObject shippingParams = new JSONObject();
            JSONObject addressParams = new JSONObject();
            requestParams.put("shipping", shippingParams);
            shippingParams.put("option_id", shippingOptionId);
            shippingParams.put("address", addressParams);
            addressParams.put("city", "Moscow");
            addressParams.put("street", "пр. Ленинский");
            addressParams.put("building", "100");
            addressParams.put("block", "1");
            addressParams.put("entrance", "2");
            addressParams.put("floor", "6");
            addressParams.put("apartment", "39");
            addressParams.put("comments", "Звонить 2 раза");
            addressParams.put("lat", "55.794816");
            addressParams.put("lon", "37.796086");
            addressParams.put("kind", "home");
            addressParams.put("door_phone", "32");
            addressParams.put("delivery_to_door", "false");
            JSONObject paymentParams = new JSONObject();
            requestParams.put("payment", paymentParams);
            paymentParams.put("option_id", paymentOptionId);
            JSONObject replacementParams = new JSONObject();
            requestParams.put("replacement", replacementParams);
            replacementParams.put("option_id", replacementOptionId);
            return givenWithSpec()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version", "3.0")
                    .header("Client-Token", "14cd5d341d768bd4926fc9f5ce262094")
                    .post(ApiV3Endpoints.Orders.DELIVERY);
        }
    }
}
