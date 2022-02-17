package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.request.ApiV3RequestBase;

import java.util.UUID;

import static ru.instamart.api.helper.ApiV3Helper.getApiClienToken;

@SuppressWarnings("unchecked")
public class OrderV3Request extends ApiV3RequestBase {
    /**
     *  Получение ордера по UUID
     */
    @Step("{method} /" + ApiV3Endpoints.Orders.ORDER_BY_UUID)
    public static Response GET(String UUID, String clientToken)
    {
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .header("Api-Version","3.0")
                .header("Client-Token", clientToken)
                .get(ApiV3Endpoints.Orders.ORDER_BY_UUID,UUID);
    }

    public static Response GET(String UUID)
    {
        return GET(UUID, getApiClienToken("goods"));
    }

    public static class PickupFromStore {
        /**
         * Создание заказа замовывоз
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.PICKUP_FROM_STORE)
        public static Response POST(String paymentOptionId, String shippingMethodOptions, String replacementOptionId, ApiV3TestData testData) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("number", UUID.randomUUID());
            requestParams.put("ship_total", testData.getShipTotal());
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", testData.getItemId());
            itemParams.put("quantity", testData.getItemQuantity());
            itemParams.put("price", testData.getItemDiscount());
            itemParams.put("discount", testData.getItemDiscount());
            JSONObject contactParams = new JSONObject();
            requestParams.put("contact", contactParams);
            contactParams.put("first_name","Qasper");
            contactParams.put("last_name","Tester");
            contactParams.put("email","Qasper.Tester@yandex.ru");
            contactParams.put("phone","79268202951");
            JSONObject shippingParams = new JSONObject();
            requestParams.put("shipping", shippingParams);
            shippingParams.put("option_id", shippingMethodOptions);
            JSONObject paymentParams = new JSONObject();
            requestParams.put("payment", paymentParams);
            paymentParams.put( "option_id", paymentOptionId);
            JSONObject replacementParams = new JSONObject();
            requestParams.put("replacement", replacementParams);
            replacementParams.put( "option_id", replacementOptionId);
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token",testData.getClientToken())
                    .post(ApiV3Endpoints.Orders.PICKUP_FROM_STORE);
        }
    }

    public static class Cancel {
        /**
         *  Отмена заказа
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.CANCEL)
        public static Response PUT(String UUID, String clientToken)
        {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status","canceled");
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .header("Api-Version","3.0")
                    .header("Client-Token", clientToken)
                    .body(requestParams)
                    .put(ApiV3Endpoints.Orders.CANCEL,UUID);
        }

        public static Response PUT(String UUID)
        {
            return PUT(UUID, getApiClienToken("sber_devices"));
        }
    }

    public static class Delivery {
        /**
         * Создание заказа на доставку
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.DELIVERY)
        public static Response POST(String paymentOptionId, String shippingMethodOptions, String replacementOptionId, ApiV3TestData testData) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("number", UUID.randomUUID());
            requestParams.put("ship_total", testData.getShipTotal());
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", testData.getItemId());
            itemParams.put("quantity", testData.getItemQuantity());
            itemParams.put("price", testData.getItemPrice());
            itemParams.put("discount", testData.getItemDiscount());
            itemParams.put("promo_total", testData.getItemPromoTotal());
            JSONObject contactParams = new JSONObject();
            requestParams.put("contact", contactParams);
            contactParams.put("first_name", "Qasper");
            contactParams.put("last_name", "Tester");
            contactParams.put("email", "Qasper.Tester@yandex.ru");
            contactParams.put("phone", "79268202951");
            JSONObject shippingParams = new JSONObject();
            JSONObject addressParams = new JSONObject();
            requestParams.put("shipping", shippingParams);
            shippingParams.put("option_id", shippingMethodOptions);
            shippingParams.put("address", addressParams);
            addressParams.put("city", "Moscow");
            addressParams.put("street", "пр. Ленинский");
            addressParams.put("building", "100");
            addressParams.put("block", "1");
            addressParams.put("entrance", "2");
            addressParams.put("floor", "6");
            addressParams.put("apartment", "39");
            addressParams.put("comments", "Звонить 2 раза");
            addressParams.put("lat", 55.747581);
            addressParams.put("lon", 37.797110);
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
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version", "3.0")
                    .header("Client-Token", testData.getClientToken())
                    .post(ApiV3Endpoints.Orders.DELIVERY);
        }
    }
}
