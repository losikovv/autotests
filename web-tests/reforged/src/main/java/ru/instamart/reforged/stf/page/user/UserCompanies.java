package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.Page;

public final class UserCompanies implements Page {

    private static final String PAGE = "/user/companies";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
