package ru.instamart.reforged.next.page.landings.drivers_hiring;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;
import static ru.instamart.reforged.next.page.landings.drivers_hiring.DriversHiringElement.DriverJobForm.*;

public interface DriversHiringCheck extends Check, DriversHiringElement {

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkSubmitButton() {
        waitAction().shouldBeVisible(submitButton);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkNameField() {
        waitAction().shouldBeVisible(nameField);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkCitySelector() {
        waitAction().shouldBeVisible(citySelector);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkCountrySelector() {
        waitAction().shouldBeVisible(countrySelector);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkPhoneField() {
        waitAction().shouldBeVisible(phoneField);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkWorkConditions() {
        waitAction().shouldBeVisible(workConditions);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkHowToJoin() {
        waitAction().shouldBeVisible(howToJoin);
    }

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkWhatToDo() {
        waitAction().shouldBeVisible(whatToDo);
    }


}
