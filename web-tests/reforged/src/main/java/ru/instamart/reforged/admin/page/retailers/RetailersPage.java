package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.admin.AdminPage;

public final class RetailersPage implements AdminPage, RetailersPageCheck {

    @Step("Вернуть количество ретейлеров на странице ретейлеров")
    public Integer retailerQuantityReturn() {
        return retailersInTable.elementCount();
    }

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
