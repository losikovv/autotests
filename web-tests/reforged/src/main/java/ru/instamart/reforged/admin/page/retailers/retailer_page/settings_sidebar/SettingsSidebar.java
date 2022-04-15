package ru.instamart.reforged.admin.page.retailers.retailer_page.settings_sidebar;

import io.qameta.allure.Step;

public class SettingsSidebar implements SettingsSidebarCheck {

    @Step("Нажимаем 'Применить'")
    public void clickSubmit() {
        submit.click();
    }

    @Step("Нажимаем 'Отменить'")
    public void clickCancel() {
        cancel.click();
    }

}
