package ru.instamart.reforged.stf.page.landings.job;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.stf.page.StfPage;

public final class JobPage implements StfPage {

    @Override
    public String pageUrl() {
        return EnvironmentProperties.Env.FULL_JOB_LANDING_URL;
    }
}
