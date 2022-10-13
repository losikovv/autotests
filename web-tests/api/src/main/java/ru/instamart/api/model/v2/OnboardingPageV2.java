package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPageV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    private String label;

    @JsonProperty(value = "label_color")
    private String labelColor;

    @Null
    @JsonSchema(required = true)
    private String title;

    @Null
    @JsonSchema(required = true)
    private String description;

    @JsonProperty(value = "background_color")
    private String backgroundColor;

    @JsonSchema(required = true)
    private String condition;

    @JsonSchema(required = true)
    private String image;

    @JsonProperty(value = "app_version")
    private String appVersion;

    @JsonProperty(value = "button_text")
    private String buttonText;

    @Null
    @JsonProperty(value = "button_url")
    private String buttonUrl;

    @JsonProperty(value = "is_black")
    private Boolean isBlack;

    @JsonProperty(value = "for_new_user_only")
    private Boolean forNewUserOnly;

    @JsonProperty(value = "view_count")
    private Integer viewCount;
}
