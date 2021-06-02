package ru.instamart.api.model.v2.ExternalPartnersV2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class CheckoutV2 extends BaseObject {

    @JsonProperty("text")
    private String text;
    @JsonProperty("promocode")
    private String promocode;
}
