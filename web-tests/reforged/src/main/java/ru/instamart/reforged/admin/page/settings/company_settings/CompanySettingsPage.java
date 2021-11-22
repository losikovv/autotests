package ru.instamart.reforged.admin.page.settings.company_settings;

import ru.instamart.reforged.admin.AdminPage;

public class CompanySettingsPage implements AdminPage, CompanySettingsCheck {
    @Override
    public String pageUrl() {
        return "company_settings/edit";
    }
}
