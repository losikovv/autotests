package ru.instamart.reforged.admin.page.settings.all_cities;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AllCitiesCheck extends Check, AllCitiesElements {

    @Step
    default void checkAddCityAlertVisible() {
        waitAction().shouldBeVisible(addCityAlert);
    }

    @Step
    default void checkDeleteCityAlertVisible() {
        waitAction().shouldBeVisible(deleteCityAlert);
    }
}
