package ru.instamart.api.model.v2.external_partners;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrimeCategoryWithoutSubscriptionV2 extends BaseObject {
    private String header;
    private String text;
    private String link;
    @JsonProperty("link_text")
    private String linkText;
    @JsonProperty("close_button_text")
    private String closeButtonText;

}
