package ru.instamart.reforged.next.page.landings.job;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.next.page.StfPage;

public final class JobPage implements StfPage {

    @Override
    public String pageUrl() {
        return EnvironmentProperties.Env.FULL_JOB_LANDING_URL;
    }
}
