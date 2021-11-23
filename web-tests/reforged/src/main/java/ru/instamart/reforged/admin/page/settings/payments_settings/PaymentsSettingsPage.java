package ru.instamart.reforged.admin.page.settings.payments_settings;

import ru.instamart.reforged.admin.AdminPage;

public class PaymentsSettingsPage implements AdminPage, PaymentsSettingsCheck {
    @Override
    public String pageUrl() {
        return "payment_methods";
    }
}
