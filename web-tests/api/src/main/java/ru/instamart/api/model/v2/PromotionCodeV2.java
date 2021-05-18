package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PromotionCodeV2 extends BaseObject {

    @JsonProperty(value = "value")
    private String code;
    private String description;
}
