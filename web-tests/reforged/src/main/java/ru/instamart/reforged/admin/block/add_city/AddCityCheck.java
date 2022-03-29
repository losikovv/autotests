package ru.instamart.reforged.admin.block.add_city;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AddCityCheck extends Check, AddCityElement{

    @Step("Проверяем, что Название страницы отображается")
    default void checkModalTitleVisible() {
        waitAction().shouldBeVisible(modalTitle);
    }

    @Step("Проверяем, что модальное окно создания города отображается")
    default void checkModalOpen() {
        waitAction().shouldBeVisible(modal);
    }

    @Step("Проверяем, что кнопка закрытия добавления нового города отображается")
    default void checkCloseButtonVisible() {
        waitAction().shouldBeVisible(closeButton);
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
    default void checkCityNameToInputVisible() {
        waitAction().shouldBeVisible(cityNameToInput);
    }

    @Step("Проверяем, что лейбл ввода ссылки отображается")
    default void checkCityLinkLabelVisible() {
        waitAction().shouldBeVisible(cityLinkLabel);
    }

    @Step("Проверяем, что инпут ввода ссылки отображается")
    default void checkCityLinkInputVisible() {
        waitAction().shouldBeVisible(cityLinkInput);
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
