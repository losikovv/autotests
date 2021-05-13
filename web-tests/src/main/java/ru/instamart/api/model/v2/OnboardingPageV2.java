package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPageV2 extends BaseObject {
    private Integer id;
    private String label;
    @JsonProperty(value = "label_color")
    private String labelColor;
    private String title;
    private String description;
    @JsonProperty(value = "background_color")
    private String backgroundColor;
    private String condition;
    private String image;
    @JsonProperty(value = "app_version")
    private String appVersion;
}
