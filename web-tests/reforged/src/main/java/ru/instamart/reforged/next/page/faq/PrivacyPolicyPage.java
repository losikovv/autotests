package ru.instamart.reforged.next.page.faq;

import ru.instamart.reforged.next.page.StfPage;

public final class PrivacyPolicyPage implements StfPage {

    @Override
    public String pageUrl() {
        return "docs/personal_data_processing_policy.pdf";
    }
}
