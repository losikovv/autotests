package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.OnboardingPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OnboardingPagesResponse extends BaseResponseObject {
    @JsonProperty(value = "onboarding_pages")
    private List<OnboardingPage> onboardingPages = null;
}
