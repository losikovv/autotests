package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class RetailersPage implements AdminPage, RetailersPageCheck {

    @Step("Вернуть количество ретейлеров на странице ретейлеров")
    public Integer retailerQuantityReturn() {
        return retailersInTable.elementCount();
    }

    @Step("Нажать на ретейлера {0}")
    public void clickOnRetailer(final String retailer) {
        retailersInTable.clickOnElementWithText(retailer);
    }

    @Step("Нажать на плюс у ретейлера {0}")
    public void clickOnPlusForRetailer(final String retailer) {
        retailerPlusIconInTable.click(retailer);
    }

    @Step("Нажать на магазин {0}")
    public void clickOnStore(final String store) {
        storesInTable.clickOnElementWithText(store);
    }

    @Step("Нажать на адрес {0}")
    public void clickOnAddress(final String store) {
        addressesInTable.clickOnElementWithText(store);
    }

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
