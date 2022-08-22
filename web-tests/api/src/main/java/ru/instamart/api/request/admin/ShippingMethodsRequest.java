package ru.instamart.api.request.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;
import ru.instamart.kraken.common.Mapper;

public final class ShippingMethodsRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.SHIPPING_METHODS)
    public static Response POST(final ShippingMethod shippingMethod) {
        return givenWithAuth()
                .formParams(Mapper.INSTANCE.objectToMap(shippingMethod))
                .post(AdminEndpoints.SHIPPING_METHODS);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static final class ShippingMethod {

        @JsonProperty(value = "shipping_method[name]")
        private String name;
        @JsonProperty(value = "shipping_method[shipping_method_kind_id]")
        private Integer kindId;
        @JsonProperty(value = "shipping_method[shipping_category_ids][]")
        private Object categoryIds;
    }
}
