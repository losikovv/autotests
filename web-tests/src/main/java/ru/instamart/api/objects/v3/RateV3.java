package ru.instamart.api.objects.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RateV3 extends BaseObject {
    private String type;
    private String title;
    private String detail;
    private Integer price;
    @JsonProperty("original_price")
    private Integer originalPrice;
}
