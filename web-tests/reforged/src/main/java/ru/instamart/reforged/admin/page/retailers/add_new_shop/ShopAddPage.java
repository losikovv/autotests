package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;

public class ShopAddPage implements AdminPage, ShopAddCheck {

    public void goToPage(final ShopUrl shop) {
        Kraken.open(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + "retailers/" + shop + "/stores/new");
    }

    @Step("Выбираем тестовый регион: {0} в дропдауне регионов")
    public void selectTestRegionInRegionsDropdown(String cityName) {
        regionsDropdown.selectByText(cityName);
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
