package ru.instamart.reforged.stf.page.landings.job;

import ru.instamart.reforged.core.config.BasicProperties;
import ru.instamart.reforged.stf.page.StfPage;

public final class JobPage implements StfPage {

    @Override
    public String pageUrl() {
        return BasicProperties.JOB_LANDING_URL;
    }
}
