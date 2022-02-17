package ru.instamart.reforged.business.page.user.profile;

import ru.instamart.reforged.business.page.BusinessPage;

public final class UserProfilePage implements BusinessPage, UserProfileCheck {

    @Override
    public String pageUrl() {
        return "user/edit";
    }
}
