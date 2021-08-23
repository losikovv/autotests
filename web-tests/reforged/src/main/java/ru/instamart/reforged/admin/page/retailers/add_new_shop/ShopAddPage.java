package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.page.shop.ShopPage;

public class ShopAddPage implements AdminPage, ShopAddCheck {

    public void goToPage(final ShopPage.ShopUrl shop) {
        Kraken.open(EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + "retailers/" + shop + "/stores/new");
    }

    @Step("Выбираем тестовый регион в дропдауне регионов")
    public void selectTestRegionInRegionsDropdown() {
        regionsDropdown.selectByText("АвтотестГород");
    }

    @Override
    public void goToPage() {
        goToPage(ShopPage.ShopUrl.DEFAULT);
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @RequiredArgsConstructor
    @Getter
    public enum ShopUrl {
        DEFAULT(Config.DEFAULT_RETAILER),
        METRO("metro"),
        LENTA("lenta"),
        AUCHAN("auchan"),
        VKUSVILL("vkusvill");

        private final String url;
    }
}
