package ru.instamart.reforged.stf.page.user.profile;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserProfilePage implements StfPage, UserProfileCheck{

    @Override
    public String pageUrl() {
        return "user/edit";
    }
}
