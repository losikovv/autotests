package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.OnboardingPageV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPagesV2Response extends BaseResponseObject {
    @JsonProperty(value = "onboarding_pages")
    private List<OnboardingPageV2> onboardingPages = null;
}
