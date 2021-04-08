package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromotionCard extends BaseObject {
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
    private Image backgroundImage;
}
