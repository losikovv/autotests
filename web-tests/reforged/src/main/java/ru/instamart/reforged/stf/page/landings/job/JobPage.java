package ru.instamart.reforged.stf.page.landings.job;

import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.stf.page.StfPage;

public final class JobPage implements StfPage {

    @Override
    public String pageUrl() {
        return UiProperties.JOB_LANDING_URL;
    }
}
