package ru.instamart.reforged.stf.page;

import ru.instamart.kraken.data.user.UserData;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.cdp.CdpCookie;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.page.Page;

public interface StfPage extends Page {

    default void goToPageWithAuth(final UserData userData) {
        goToPageWithAuth(pageUrl(), userData);
    }

    default void goToPageWithAuth(final String url, final UserData userData) {
        CdpCookie.addCookie(CookieFactory.authStf(userData));
        goToPage(url);
    }

    default void goToPage() {
        goToPage(pageUrl());
    }

    default void goToPage(final String url) {
        Kraken.open(addBasicAuthToUrl(UiProperties.STF_URL + url));
    }
}
