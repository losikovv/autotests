package ru.instamart.reforged.admin.page.retailers.retailer_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class RetailerPage implements AdminPage, RetailerPageCheck{

    @Step("Нажать на магазин {0}")
    public void clickOnStore(final String store) {
        storesInTable.clickOnElementWithText(store);
    }

    @Step("Нажать на адрес магазина {0}")
    public void clickOnAddress(final String address) {
        addressesInTable.clickOnElementWithText(address);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
