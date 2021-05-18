package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextDeliveryV2 extends BaseObject {
    private Integer id;
    @JsonProperty(value = "store_id")
    private Integer storeId;
    private Double price;
    private String summary;
    @JsonProperty(value = "starts_at")
    private String startsAt;
    @JsonProperty(value = "ends_at")
    private String endsAt;
    private String kind;
}
