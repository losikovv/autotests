package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_edit;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

public class StoreEditPage implements AdminPage, StoreEditCheck {

    public void goToPage(final String retailer, final String storeUUID) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD + "retailers/" + retailer + "/stores/" + storeUUID + "/edit");
    }

    @Step("Кликаем на группу магазинов: {storeLabel}")
    public void clickOnStoreLabel(final String storeLabel) {
        storeGroups.clickOnElementWithText(storeLabel.toUpperCase());
    }

    @Step("Кликаем 'Изменить'")
    public void clickSubmit() {
        submit.hoverAndClick();
    }

    @Override
    public void goToPage() {
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
