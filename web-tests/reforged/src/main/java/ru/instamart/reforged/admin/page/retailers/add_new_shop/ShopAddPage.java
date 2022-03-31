package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;

public class ShopAddPage implements AdminPage, ShopAddCheck {

    public void goToPage(final String shop) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH_OLD + "retailers/" + shop + "/stores/new");
    }

    @Step("Выбираем тестовый регион: {0} в дропдауне регионов")
    public void selectTestRegionInRegionsDropdown(String cityName) {
        regionsDropdown.selectByText(cityName);
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
