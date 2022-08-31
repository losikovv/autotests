package ru.instamart.reforged.admin.page.shoppers;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class ShoppersPage implements AdminPage, ShoppersCheck {

    @Step("Нажать на кнопку 'Добавить сотрудника'")
    public void clickToCreateShoppers() {
        createShoppersButton.click();
    }

    @Override
    public String pageUrl() {
        return "shoppers";
    }
}
