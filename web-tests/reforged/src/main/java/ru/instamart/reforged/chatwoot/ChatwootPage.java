package ru.instamart.reforged.chatwoot;

import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface ChatwootPage extends ChatwootCheck, Page {


    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(UiProperties.CHATWOOT_URL.replaceAll("sbermarket.ru", "do_not_run_on.prod") + url);
    }
}
