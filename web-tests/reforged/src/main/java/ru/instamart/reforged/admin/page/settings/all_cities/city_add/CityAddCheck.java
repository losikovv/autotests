package ru.instamart.reforged.admin.page.settings.all_cities.city_add;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CityAddCheck extends Check, CityAddElements {

    @Step("Проверяем, что Название страницы отображается")
    default void checkPageTitleVisible() {
        waitAction().shouldBeVisible(pageTitle);
    }

    @Step("Проверяем, что кнопка отмены добавления нового города отображается")
    default void checkReturnButtonVisible() {
        waitAction().shouldBeVisible(returnButton);
    }

    @Step("Проверяем, что лейбл для ввода имени города в имен.падеж отображается")
    default void checkCityNameInputLabelVisible() {
        waitAction().shouldBeVisible(cityNameInputLabel);
    }

    @Step("Проверяем, что инпут для ввода имени города в имен.падеже отображается")
    default void checkCityNameInputVisible() {
        waitAction().shouldBeVisible(cityNameInput);
    }

    @Step("Проверяем, что лейбл для ввода имени города в предл.падеже отображается")
    default void checkCityNameInInputLabelVisible() {
        waitAction().shouldBeVisible(cityNameInInputLabel);
    }

    @Step("Проверяем, что инпут ввода имени города в предл.падеже отображается")
    default void checkCityNameInInputVisible() {
        waitAction().shouldBeVisible(cityNameInInput);
    }

    @Step("Проверяем, что лейбл для ввода имени города в родит.падеже отображается")
    default void checkCityNameFromInputLabelVisible() {
        waitAction().shouldBeVisible(cityNameFromInputLabel);
    }

    @Step("Проверяем, что инпут ввода имени города в родит.падеже отображается")
    default void checkCityNameFromInputVisible() {
        waitAction().shouldBeVisible(cityNameFromInput);
    }

    @Step("Проверяем, что лейбл для ввода имени города в направит.падеже отображается")
    default void checkCityNameToInputLabelVisible() {
        waitAction().shouldBeVisible(cityNameToInputLabel);
    }

    @Step("Проверяем, что инпут ввода имени города в направит.падеже отображается")
    default void checkcityNameToInputVisible() {
        waitAction().shouldBeVisible(cityNameToInput);
    }

    @Step("Проверяем, что лейбл чекбокса блокировки города отображается")
    default void checkCityLockedLabelVisible() {
        waitAction().shouldBeVisible(cityLockedLabel);
    }

    @Step("Проверяем, что чекбокс город заблокирован для редактирования отображается")
    default void checkCityLockedVisible() {
        waitAction().shouldBeVisible(cityLocked);
    }

    @Step("Проверяем, что кнопка подтверждения добавления нового города отображается")
    default void checkCreateButtonVisible() {
        waitAction().shouldBeVisible(createButton);
    }

    @Step("Проверяем, что кнопка отмены добавления нового города отображается")
    default void checkCancelButtonVisible() {
        waitAction().shouldBeVisible(cancelButton);
    }
}
