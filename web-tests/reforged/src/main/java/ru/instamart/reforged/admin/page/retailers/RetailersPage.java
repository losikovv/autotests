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

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
