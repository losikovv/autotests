package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.enums.ShopUrl;

public class ShopAddPage implements AdminPage, ShopAddCheck {

    @Step("Вводим тестовый регион: {0} в инпут регионов")
    public void fillRegion(String cityName) {
        regionsInput.click();
        regionsInput.fill(cityName);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage("retailers/" + shop.getUrl() + "/stores/new");
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
