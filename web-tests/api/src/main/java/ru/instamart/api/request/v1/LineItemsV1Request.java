package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.LineItemV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.sbermarket.common.Mapper;

public class LineItemsV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.LINE_ITEMS)
    public static Response GET(Number number) {
        return givenWithAuth()
                .queryParams(Mapper.INSTANCE.objectToMap(number))
                .get(ApiV1Endpoints.LINE_ITEMS);
    }


    @Step("{method} /" + ApiV1Endpoints.LINE_ITEMS)
    public static Response POST(Long offerId) {
        JSONObject body = new JSONObject();
        JSONObject lineItem = new JSONObject();
        lineItem.put("offer_id", offerId);
        body.put("line_item", lineItem);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .log().all()
                .post(ApiV1Endpoints.LINE_ITEMS);
    }

    @Step("{method} /" + ApiV1Endpoints.LineItems.BY_ID)
    public static Response PUT(LineItemV1 lineItem) {
        JSONObject body = new JSONObject();
        body.put("line_item", lineItem);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.LineItems.BY_ID, lineItem.getId());
    }

    @Step("{method} /" + ApiV1Endpoints.LineItems.BY_ID)
    public static Response DELETE(Long lineItemId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.LineItems.BY_ID, lineItemId);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class Number {

        @JsonProperty(value = "order_number")
        private String orderNumber;
        @JsonProperty(value = "shipment_number")
        private String shipmentNumber;
    }
}
