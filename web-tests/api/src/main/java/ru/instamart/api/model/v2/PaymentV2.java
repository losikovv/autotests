package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentV2 extends BaseObject {
    private String number;
    private String state;
    @JsonProperty(value = "is_finalization_needed")
    private Boolean isFinalizationNeeded;
    @JsonProperty(value = "finalization_url")
    private Object finalizationUrl;
}
