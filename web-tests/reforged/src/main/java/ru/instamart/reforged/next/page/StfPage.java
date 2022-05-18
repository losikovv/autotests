package ru.instamart.reforged.next.page;

import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Page;

public interface StfPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + url);
    }

    default void goToPageFromTenant() {
        goToPageFromTenant(pageUrl());
    }

    default void goToPageFromTenant(final String url) {
        Kraken.open(EnvironmentProperties.Env.FULL_B2C_URL_WITH_BASIC_AUTH + url);
    }
}
