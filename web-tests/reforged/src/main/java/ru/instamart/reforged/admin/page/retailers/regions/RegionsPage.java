package ru.instamart.reforged.admin.page.retailers.regions;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.retailers.regions.add_new.RegionsAdd;

public class RegionsPage implements AdminPage, RegionsCheck {

    public RegionsAdd interactRegionsAddModal() {
        return regionsAdd;
    }

    @Step("Нажатие на кнопку добавления нового региона")
    public void clickToAddNewRegion() {
        addNewRegionButton.hoverAndClick();
    }

    @Step("Нажатие на кнопку удаления у тестового региона: {0}")
    public void deleteTestRegion(final String cityName) {
        removeRegion.click(cityName);
    }

    @Override
    public String pageUrl() {
        return "operational_zones";
    }
}
