package ru.instamart.reforged.admin.page.retailers.regions;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.Kraken;

public class RegionsPage implements AdminPage, RegionsCheck {

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
