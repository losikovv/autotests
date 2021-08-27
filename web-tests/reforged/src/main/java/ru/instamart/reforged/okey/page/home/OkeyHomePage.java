package ru.instamart.reforged.okey.page.home;

import ru.instamart.reforged.okey.block.footer.Footer;
import ru.instamart.reforged.okey.block.header.Header;
import ru.instamart.reforged.okey.page.OkeyPage;

public final class OkeyHomePage implements OkeyPage, OkeyHomeCheck {

    @Override
    public String pageUrl() {
        return "okey";
    }

    public Header interactHeader() {
        return header;
    }

    public Footer interactFooter() {
        return footer;
    }
}