package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.Page;

public final class UserFavorites implements Page {

    private static final String PAGE = "/user/favorites";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
