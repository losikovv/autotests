package ru.instamart.reforged.stf.page.user;

import ru.instamart.reforged.stf.page.StfPage;

public final class UserCompanies implements StfPage {

    private static final String PAGE = "/user/companies";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
