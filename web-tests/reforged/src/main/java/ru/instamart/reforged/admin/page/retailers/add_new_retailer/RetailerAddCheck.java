package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailerAddCheck extends Check, RetailerAddElements {

    @Step("Проверяем, что инпут ввода имени отображается")
    default void checkNameInputVisible() {
        waitAction().shouldBeVisible(nameInput);
    }
}
