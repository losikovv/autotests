package ru.instamart.api.request.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.common.Mapper;

import java.io.File;
import java.util.List;

public class PromotionCardsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.PROMOTION_CARDS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.PROMOTION_CARDS);
    }

    @Step("{method} /" + ApiV1Endpoints.PROMOTION_CARDS)
    public static Response POST(PromotionCard promotionCard, List<Integer> storeIds) {
        RequestSpecification req = givenWithAuth();
        storeIds.forEach(id -> req.formParam("promotion_card[store_ids][]", id));
        return req
                .formParams(Mapper.INSTANCE.objectToMap(promotionCard))
                .post(ApiV1Endpoints.PROMOTION_CARDS);
    }

    @Step("{method} /" + ApiV1Endpoints.PromotionCards.ID)
    public static Response GET(Long promotionCardId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.PromotionCards.ID, promotionCardId);
    }

    @Step("{method} /" + ApiV1Endpoints.PromotionCards.ID)
    public static Response PUT(Long promotionCardId, PromotionCard promotionCard, String fileName) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(promotionCard))
                .multiPart("promotion_card[background_image_attributes][attachment]", new File(fileName), "image/jpeg")
                .put(ApiV1Endpoints.PromotionCards.ID, promotionCardId);
    }

    @Step("{method} /" + ApiV1Endpoints.PromotionCards.ID)
    public static Response DELETE(Long promotionCardId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.PromotionCards.ID, promotionCardId);
    }

    @Step("{method} /" + ApiV1Endpoints.PromotionCards.UPDATE_POSITIONS)
    public static Response PUT(Long promotionCardId, int positionNumber) {
        JSONObject body = new JSONObject();
        JSONArray positions = new JSONArray();
        JSONObject position = new JSONObject();
        position.put("id", promotionCardId);
        position.put("position", positionNumber);
        positions.add(position);
        body.put("positions", positions);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.PromotionCards.UPDATE_POSITIONS);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PromotionCard {
        @JsonProperty("promotion_card[type]")
        private String type;
        @JsonProperty("promotion_card[title]")
        private String title;
        @JsonProperty("promotion_card[short_description]")
        private String shortDescription;
        @JsonProperty("promotion_card[full_description]")
        private String fullDescription;
        @JsonProperty("promotion_card[background_color]")
        private String backgroundColor;
        @JsonProperty("promotion_card[message_color]")
        private String messageColor;
        @JsonProperty("promotion_card[published]")
        private Boolean published;
        @JsonProperty("promotion_card[mobile_app_link]")
        private String mobileAppLink;
        @JsonProperty("promotion_card[web_app_link]")
        private String webAppLink;
        @JsonProperty("promotion_card[category_id]")
        private Long categoryId;
        @JsonProperty("promotion_card[promotion_id]")
        private Long promotionId;
        @JsonProperty(value = "promotion_card[tenant_ids][]")
        private String tenantIds;
    }
}
