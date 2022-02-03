package ru.instamart.reforged.admin.page.settings.sms_settings;

import ru.instamart.reforged.admin.AdminPage;

public class SmsSettingsPage implements AdminPage, SmsSettingsCheck {

    @Override
    public String pageUrl() {
        return "sms_settings/edit";
    }
}
