package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.Page;

public final class UserEdit implements Page {

    private static final String PAGE = "/user/edit";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
