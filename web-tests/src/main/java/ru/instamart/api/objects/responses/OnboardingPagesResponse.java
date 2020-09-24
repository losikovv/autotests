package instamart.api.objects.responses;

import instamart.api.objects.OnboardingPage;

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
