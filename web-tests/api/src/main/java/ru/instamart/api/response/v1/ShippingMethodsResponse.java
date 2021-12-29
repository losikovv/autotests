package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.api.request.admin.ShippingMethodsRequest;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ShippingMethodsResponse extends BaseResponseObject {

    @JsonProperty(value = "shipping_methods")
    private List<ShippingMethods> shippingMethods;

    @Data
    public static final class ShippingMethods {
        private String name;
        private Kind kind;
        private int id;
    }

    @RequiredArgsConstructor
    public enum Kind {

        BY_COURIER("by_courier"),
        BY_COURIER_FOR_COMPANIES("by_courier_for_companies"),
        PICKUP("pickup");

        @Getter(onMethod_={@JsonValue})
        private final String type;
    }
}
