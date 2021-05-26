package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.Page;

public final class UserShipments implements Page {

    private static final String PAGE = "/user/shipments";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
