package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCardV2 extends BaseObject {
    private Integer id;
    private String title;
    @JsonProperty(value = "short_description")
    private String shortDescription;
    @JsonProperty(value = "full_description")
    private String fullDescription;
    @JsonProperty(value = "background_color")
    private String backgroundColor;
    @JsonProperty(value = "text_color")
    private String textColor;
    private String type;
    @JsonProperty(value = "promotion_id")
    private Integer promotionId;
    @JsonProperty(value = "mobile_link")
    private Object mobileLink;
    private String tag;
    private Integer position;
    @JsonProperty(value = "background_image")
    private ImageV2 backgroundImage;
}
