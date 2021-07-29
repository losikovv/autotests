package ru.instamart.reforged.stf.page.user.favorites;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserFavoritesPage implements StfPage, UserFavoritesCheck {

    @Override
    public String pageUrl() {
        return "/user/favorites";
    }
}
