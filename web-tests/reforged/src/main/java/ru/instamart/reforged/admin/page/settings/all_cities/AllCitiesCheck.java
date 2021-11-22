package ru.instamart.reforged.admin.page.settings.all_cities;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AllCitiesCheck extends Check, AllCitiesElements {

    @Step("Проверяем, что алерт добавления города отображается")
    default void checkAddCityAlertVisible() {
        waitAction().shouldBeVisible(addCityAlert);
    }

    @Step("Проверяем, что алерт удаления города отображается")
    default void checkDeleteCityAlertVisible() {
        waitAction().shouldBeVisible(deleteCityAlert);
    }

    @Step("Проверяем, что заголовок страницы отображается")
    default void checkPageHeaderVisible() {
        waitAction().shouldBeVisible(pageHeader);
    }

    @Step("Проверяем, что кнопка добавления города отображается")
    default void checkAddCityButtonVisible() {
        waitAction().shouldBeVisible(addCityButton);
    }

    @Step("Проверяем, что алерт удаления города отображается")
    default void checkListingCitiesTableVisible() {
        waitAction().shouldBeVisible(listingCitiesTable);
    }
}
