package ru.instamart.reforged.selgros.page.home;

import ru.instamart.reforged.selgros.block.footer.Footer;
import ru.instamart.reforged.selgros.block.header.Header;
import ru.instamart.reforged.selgros.page.SelgrosPage;

public final class SelgrosHomePage implements SelgrosPage, SelgrosHomeCheck {

    public Header interactHeader() {
        return header;
    }

    public Footer interactFooter() {
        return footer;
    }

    public void goToPage(final boolean isFixedUUID) {
        cookiesChange(true);
        goToPage(pageUrl());
    }

    @Override
    public void goToPage() {
        cookiesChange(false);
        goToPage(pageUrl());
    }

    @Override
    public String pageUrl() {
        return "selgros";
    }
}