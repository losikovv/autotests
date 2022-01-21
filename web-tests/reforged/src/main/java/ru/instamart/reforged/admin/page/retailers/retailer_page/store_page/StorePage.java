package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class StorePage implements AdminPage, StorePageCheck {

    @Step("Нажать на кнопку 'Зоны'")
    public void clickOnRetailerZoneButton() {
        storeZones.click();
    }

    @Step("Вернуть значения названия магазина со страницы магазина")
    public String returnStoreName() {
        return storeTitle.getText();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
