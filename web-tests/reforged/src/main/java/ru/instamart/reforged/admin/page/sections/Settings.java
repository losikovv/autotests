package ru.instamart.reforged.admin.page.sections;

import ru.instamart.reforged.admin.page.AdminPage;

public final class Settings implements AdminPage {
    @Override
    public String pageUrl() {
        return "general_settings/edit";
    }
}
