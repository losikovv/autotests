package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserEdit implements StfPage {

    private static final String PAGE = "/user/edit";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
