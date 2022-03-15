package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.StfPage;

public final class PrivacyPolicyPage implements StfPage {

    @Override
    public String pageUrl() {
        return "docs/personal_data_processing_policy.pdf";
    }
}
