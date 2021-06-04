package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DetailsV2 extends BaseObject {
    @JsonProperty(value = "min_amount")
    private Long minAmount;
    @JsonProperty(value = "remaining_amount")
    private Long remainingAmount;
}
