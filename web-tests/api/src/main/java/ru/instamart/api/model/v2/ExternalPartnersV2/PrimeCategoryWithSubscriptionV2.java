package ru.instamart.api.model.v2.ExternalPartnersV2;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrimeCategoryWithSubscriptionV2 extends BaseObject {
    private String header;
    private String text;
    private String promocode;
    @JsonProperty("close_button_text")
    private String closeButtonText;
}
