package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;

public class ShopAddPage implements AdminPage, ShopAddCheck {

    public void goToPage(final String shop) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + "retailers/" + shop + "/stores/new");
    }

    @Step("Вводим тестовый регион: {0} в инпут регионов")
    public void fillRegion(String cityName) {
        regionsInput.click();
        regionsInput.fill(cityName);
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
