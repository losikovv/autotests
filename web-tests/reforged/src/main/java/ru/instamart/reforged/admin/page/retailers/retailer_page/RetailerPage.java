package ru.instamart.reforged.admin.page.retailers.retailer_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.retailers.retailer_page.appearance_sidebar.AppearanceSidebar;
import ru.instamart.reforged.admin.page.retailers.retailer_page.settings_sidebar.SettingsSidebar;

public final class RetailerPage implements AdminPage, RetailerPageCheck {

    public SettingsSidebar interactiveSettings() {
        return settingsSidebar;
    }

    public AppearanceSidebar interactiveAppearance() {
        return appearanceSidebar;
    }

    @Step("Нажать на магазин {0}")
    public void clickOnStore(final String store) {
        storesInTable.clickOnElementWithText(store);
    }

    @Step("Нажать на первый магазин")
    public void clickOnFirstStore() {
        storesInTable.clickOnFirst();
    }

    @Step("Нажать на адрес {0}")
    public void clickOnAddress(final String address) {
        addressInTableWithText.click(address);
    }

    @Step("Нажать на первый адрес")
    public void clickOnFirstAddress() {
        addressInTable.click();
    }

    @Step("Вернуть текст первого адреса")
    public String getFirstAddressFromTable() {
        return addressesInTable.getElementText(0);
    }

    @Step("Нажимаем на кнопку 'Настройки ритейлера'")
    public void clickOnSettings() {
        retailerSettings.click();
    }

    @Step("Нажимаем на кнопку 'Внешний вид'")
    public void clickOnAppearance() {
        retailerAppearance.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
