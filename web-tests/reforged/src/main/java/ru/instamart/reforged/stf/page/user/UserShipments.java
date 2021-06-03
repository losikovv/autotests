package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserShipments implements StfPage {

    private static final String PAGE = "/user/shipments";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
