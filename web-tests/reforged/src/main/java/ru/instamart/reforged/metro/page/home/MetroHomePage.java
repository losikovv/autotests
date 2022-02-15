package ru.instamart.reforged.metro.page.home;

import ru.instamart.reforged.metro.block.footer.Footer;
import ru.instamart.reforged.metro.block.header.Header;
import ru.instamart.reforged.metro.page.MetroPage;

public final class MetroHomePage implements MetroPage, MetroHomeCheck {

    public Header interactHeader() {
        return header;
    }

    public Footer interactFooter() {
        return footer;
    }

    public void goToPage(final boolean isFixedUUID) {
        goToPage(pageUrl());
        cookiesChange(true);
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
        cookiesChange(false);
    }

    @Override
    public String pageUrl() {
        return "metro";
    }
}