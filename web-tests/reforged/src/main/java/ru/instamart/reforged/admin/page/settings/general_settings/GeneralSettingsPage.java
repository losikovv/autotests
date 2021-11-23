package ru.instamart.reforged.admin.page.settings.general_settings;

import ru.instamart.reforged.admin.AdminPage;

public final class GeneralSettingsPage implements AdminPage, GeneralSettingsCheck {

    @Override
    public String pageUrl() {
        return "general_settings/edit";
    }
}
