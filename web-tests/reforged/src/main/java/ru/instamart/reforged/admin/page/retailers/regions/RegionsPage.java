package ru.instamart.reforged.admin.page.retailers.regions;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class RegionsPage implements AdminPage, RegionsCheck {

    @Step("Нажатие на кнопку добавления нового региона")
    public void clickToAddNewRegion() {
        addNewRegionButton.hoverAndClick();
    }

    @Step("Нажатие на кнопку удаления у тестового региона")
    public void deleteTestRegion() {
        deleteTestRegionButton.hoverAndClick();
    }

    @Override
    public String pageUrl() {
        return "operational_zones";
    }
}
