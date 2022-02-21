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
import org.apache.commons.lang3.RandomUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.MultiretailerOrderShipmentV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.common.Mapper;

import java.util.Objects;

public class CheckoutV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.CHECKOUT)
    public static Response PUT(Long replacementPolicyId) {
        JSONObject body = new JSONObject();
        JSONObject order = new JSONObject();
        order.put("replacement_policy_id", replacementPolicyId);
        body.put("order", order);
        body.put("authenticity_token", SessionFactory.getSession(SessionType.API_V1).getToken());
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.CHECKOUT);
    }

    @Step("{method} /" + ApiV1Endpoints.CHECKOUT)
    public static Response PUT(MultiretailerOrderShipmentV1 shipment) {
        JSONObject body = new JSONObject();
        JSONObject order = new JSONObject();
        JSONArray shipmentsAttributes = new JSONArray();
        JSONObject shipmentsAttribute = new JSONObject();
        shipmentsAttribute.put("id", shipment.getId());
        shipmentsAttribute.put("delivery_window_id", shipment.getNextDeliveries().get(0).getId());
        shipmentsAttribute.put("express_delivery", Objects.nonNull(shipment.getOnDemandEstimate()));
        shipmentsAttributes.add(shipmentsAttribute);
        order.put("shipments_attributes", shipmentsAttributes);
        body.put("order", order);
        body.put("authenticity_token", SessionFactory.getSession(SessionType.API_V1).getToken());
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.CHECKOUT);
    }

    @Step("{method} /" + ApiV1Endpoints.Checkout.COMPLETE)
    public static Response PUT(OrderAttributes orderAttributes) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(orderAttributes))
                .put(ApiV1Endpoints.Checkout.COMPLETE);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrderAttributes {
        private String kind;
        private Integer apartment;
        private Integer floor;
        private Integer entrance;
        private Integer doorPhone;
        private String comments;
        @JsonProperty(value = "order[ship_address_attributes][lastname]")
        private String lastName;
        @JsonProperty(value = "user.lastName")
        private String userLastName;
        @JsonProperty(value = "order[email]")
        private String orderEmail;
        @JsonProperty(value = "user.email")
        private String userEmail;
        @JsonProperty(value = "order[ship_address_attributes][phone]")
        private String phone;
        @JsonProperty(value = "order[send_emails]")
        private String sendEmails;
        @JsonProperty(value = "order[payments_attributes][][payment_tool_id]")
        private Long paymentToolId;
        @JsonProperty(value = "order[bonus_card_action_attributes][bonus_card_id]")
        private Long bonusCardId;
    }

    public static CheckoutV1Request.OrderAttributes getOrderAttributes(UserData user, Long paymentToolId, String orderKind) {
        return CheckoutV1Request.OrderAttributes.builder()
                .kind(orderKind)
                .apartment(1)
                .floor(1)
                .entrance(1)
                .comments("Автотест-" + RandomUtils.nextInt(1, 1000))
                .lastName(user.getLastName())
                .userLastName(user.getLastName())
                .orderEmail(user.getEmail())
                .userEmail(user.getEmail())
                .phone(user.getPhone())
                .sendEmails("on")
                .paymentToolId(paymentToolId)
                .build();
    }

}
