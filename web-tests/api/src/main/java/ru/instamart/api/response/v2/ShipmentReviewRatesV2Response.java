package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShipmentReviewRatesV2Response extends BaseResponseObject {
    @JsonProperty("shipment_review")
    @JsonSchema(required = true)
    private ShipmentReview shipmentReview;

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class ShipmentReview extends BaseObject {
        @JsonSchema(required = true)
        private long id;
        @JsonSchema(required = true)
        private List<Rate> rates;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Rate extends BaseObject {
        @JsonSchema(required = true)
        private int id;
        @JsonSchema(required = true)
        private int rate;
        @JsonSchema(required = true)
        private String kind;
    }
}
