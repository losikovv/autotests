package ru.instamart.reforged.admin.page.retailers.regions.add_new;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class RegionsAdd implements AdminPage, RegionsAddCheck {

    @Step("Кликнуть на кнопку 'Создать' на модальном окне создания нового региона")
    public void clickToCreateNewRegion() {
        newRegionCreateButton.click();
    }

    @Step("Заполнить имя нового региона/города: {0} на модальном окне создания региона/города")
    public void fillNewTestRegionName(String cityName) {
        newRegionName.fill(cityName);
    }

    @Override
    public String pageUrl() {
        return "operational_zones";
    }
}
