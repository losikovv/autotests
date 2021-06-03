package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserFavorites implements StfPage {

    private static final String PAGE = "/user/favorites";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
