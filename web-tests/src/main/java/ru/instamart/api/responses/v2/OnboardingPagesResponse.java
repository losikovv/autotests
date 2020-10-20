package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.OnboardingPage;

import java.util.List;

public class OnboardingPagesResponse extends BaseResponseObject {

    private List<OnboardingPage> onboarding_pages = null;

    public List<OnboardingPage> getOnboarding_pages() {
        return onboarding_pages;
    }

    public void setOnboarding_pages(List<OnboardingPage> onboarding_pages) {
        this.onboarding_pages = onboarding_pages;
    }

}
