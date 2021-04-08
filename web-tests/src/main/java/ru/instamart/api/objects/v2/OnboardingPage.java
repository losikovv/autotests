package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPage extends BaseObject {
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
