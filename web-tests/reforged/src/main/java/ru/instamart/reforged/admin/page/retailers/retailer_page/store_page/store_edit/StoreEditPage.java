package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_edit;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class StoreEditPage implements AdminPage, StoreEditCheck {

    @Step("Кликаем на группу магазинов: {storeLabel}")
    public void clickOnStoreLabel(final String storeLabel) {
        storeGroups.clickOnElementWithText(storeLabel.toUpperCase());
    }

    @Step("Кликаем 'Изменить'")
    public void clickSubmit() {
        submit.hoverAndClick();
    }

    public void goToPage(final String retailer, final String storeUUID) {
        goToPageOld(String.format(pageUrl(), retailer, storeUUID));
    }

    @Override
    public void goToPage() {
        throw new RuntimeException("Для перехода на страницу редактирования магазина необходимо использовать метод с параметрами");
    }

    @Override
    public String pageUrl() {
        return "retailers/%s/stores/%s/edit";
    }
}
