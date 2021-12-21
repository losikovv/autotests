package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface StfPage extends Page {

    default void goToPage() {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + pageUrl());
    }

    default void excludeGuestFromAllAb() {
        Kraken.waitAction().cookieShouldBeExist(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID.getName());
        Kraken.addIfNotExist(CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID);
    }
}
