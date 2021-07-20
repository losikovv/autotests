package ru.instamart.reforged.admin.sections;

import ru.instamart.reforged.admin.AdminPage;

public final class Settings implements AdminPage {
    @Override
    public String pageUrl() {
        return "general_settings/edit";
    }
}
