package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextDelivery extends BaseObject {
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
