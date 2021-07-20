package ru.instamart.reforged.admin.sections;

import ru.instamart.reforged.admin.AdminPage;

public final class Users implements AdminPage {
    @Override
    public String pageUrl() {
        return "users";
    }
}
