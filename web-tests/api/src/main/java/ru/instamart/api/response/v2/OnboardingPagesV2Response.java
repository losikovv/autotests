package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.OnboardingPageV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPagesV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "onboarding_pages")
    private List<OnboardingPageV2> onboardingPages = null;
}
