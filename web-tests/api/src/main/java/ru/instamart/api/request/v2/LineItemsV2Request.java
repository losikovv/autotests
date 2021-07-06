package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.HashMap;
import java.util.Map;

public final class LineItemsV2Request extends ApiV2RequestBase {

    /**
     * Добавляем товар в корзину
     */
    @Step("{method} /" + ApiV2EndPoints.LINE_ITEMS)
    public static Response POST(long productId, int quantity, String orderNumber) {
        Map<String, Object> data = new HashMap<>();
        data.put("line_item[order_number]", orderNumber);
        data.put("line_item[product_id]", productId);
        data.put("line_item[quantity]", quantity);

        return givenWithAuth()
                .formParams(data)
                .post(ApiV2EndPoints.LINE_ITEMS);
    }

    /**
     * Удаляем товар из корзины
     */
    @Step("{method} /" + ApiV2EndPoints.LineItems.BY_ID)
    public static Response DELETE(long productId) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.LineItems.BY_ID, productId);
    }

    @Step("{method} /" + ApiV2EndPoints.LineItems.BY_ID)
    public static Response PUT(long productId, int qty){
        return givenWithAuth()
                .formParam("line_item[packs]", qty)
                .put(ApiV2EndPoints.LineItems.BY_ID, productId);
    }
}
