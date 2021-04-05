package instamart.api.requests.v3;

import instamart.api.endpoints.ApiV3Endpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public class OrderV3Request {
    public static class PickupFromStore {
        /**
         * Создание заказа замовывоз
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.PICKUP_FROM_STORE)
        public static Response POST(String paymentOptionId,String shippingOptionId,String replacementOptionId ) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("number", UUID.randomUUID());
           // requestParams.put("ship_total", "15000");
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", "15879");
            itemParams.put("quantity", 10);
            itemParams.put("price", 1111);
            itemParams.put("discount", 0);
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
            return givenCatch()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .post(ApiV3Endpoints.Orders.PICKUP_FROM_STORE);
        }
    }

    public static class CheckV3Request {
        /**
         *  Получение ордера по UUID
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.ORDER_BY_UUID)
        public static Response GET(String UUID)
        {

            return givenCatch()
                    .log().all()
                    .contentType(ContentType.JSON)

                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .get(ApiV3Endpoints.Orders.ORDER_BY_UUID,UUID);
        }
    }
    public static class CancelV3Request {
        /**
         *  Отмена заказа
         */
        @Step("{method} /" + ApiV3Endpoints.Orders.CANCEL)
        public static Response PUT(String UUID)
        {
            JSONObject requestParams = new JSONObject();
            requestParams.put("status","canceled");
            return givenCatch()
                    .log().all()
                    .contentType(ContentType.JSON)

                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .body(requestParams)
                    .put(ApiV3Endpoints.Orders.CANCEL,UUID);
        }
    }

}
