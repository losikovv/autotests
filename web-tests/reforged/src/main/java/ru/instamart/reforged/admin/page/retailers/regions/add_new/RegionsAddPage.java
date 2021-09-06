package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class RegionsAddPage implements AdminPage, RegionsAddCheck {

    @Step("Заполнить имя нового региона/города на странице создания региона/города")
    public void fillNewTestRegionName(String cityName) {
        newRegionName.fill(cityName);
    }

    @Step("Кликнуть на кнопку 'Создать' на странице создания нового региона")
    public void clickToCreateNewRegion() {
        newRegionCreateButton.click();
    }

    @Override
    public String pageUrl() {
        return "operational_zones/new";
    }
}
