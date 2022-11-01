package ru.instamart.reforged.hr_ops_partners.page;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface HRPartnersPage extends Page {

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.HR_PARTNERS_URL.replaceAll("sbermarket.ru", "do_not_run_on.prod") + url);
    }
}
