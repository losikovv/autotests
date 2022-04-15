package ru.instamart.reforged.admin.page.retailers.retailer_page.appearance_sidebar;

import io.qameta.allure.Step;

public class AppearanceSidebar implements AppearanceSidebarCheck {

    @Step("Нажимаем 'Применить'")
    public void clickSubmit() {
        submit.click();
    }

    @Step("Нажимаем 'Отменить'")
    public void clickCancel() {
        cancel.click();
    }

}
