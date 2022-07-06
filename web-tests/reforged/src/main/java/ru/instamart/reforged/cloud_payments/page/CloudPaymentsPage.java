package ru.instamart.reforged.cloud_payments.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface CloudPaymentsPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.DEMO_CLOUD_PAYMENTS_URL + url);
    }
}
