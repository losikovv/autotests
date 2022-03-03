package ru.instamart.reforged.business.page.user.profile;

import ru.instamart.reforged.business.page.BusinessPage;

public final class B2BUserProfilePage implements BusinessPage, B2BUserProfileCheck {

    @Override
    public String pageUrl() {
        return "user/edit";
    }
}
