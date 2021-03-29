package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class LineItemsRequest {

    /**
     * Добавляем товар в корзину
     */
    @Step("{method} /" + ApiV2EndPoints.LINE_ITEMS)
    public static Response POST(long productId, int quantity, String orderNumber) {
        Map<String, Object> data = new HashMap<>();
        data.put("line_item[order_number]", orderNumber);
        data.put("line_item[product_id]", productId);
        data.put("line_item[quantity]", quantity);

        return givenWithAuthApiV2()
                .formParams(data)
                .post(ApiV2EndPoints.LINE_ITEMS);
    }

    /**
     * Удаляем товар из корзины
     */
    @Step("{method} /" + ApiV2EndPoints.LineItems.ID)
    public static Response DELETE(long productId) {
        return givenWithAuthApiV2()
                .delete(ApiV2EndPoints.LineItems.ID, productId);
    }
}
