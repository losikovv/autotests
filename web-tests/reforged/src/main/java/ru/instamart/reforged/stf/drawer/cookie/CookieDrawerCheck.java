package ru.instamart.reforged.stf.drawer.cookie;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface CookieDrawerCheck extends Check, CookieDrawerElement {

    @Step("Проверка что отображается алерт с cookie")
    default void checkAlertVisible() {
        alert.should().visible();
    }

    @Step("Проверка что не отображается алерт с cookie")
    default void checkAlertNotVisible() {
        alert.should().invisible();
    }

    @Step("Проверка отображения кнопки в алерте")
    default void checkAlertButton() {
        alertButton.should().visible();
    }

    @Step("Проверка отображения ссылки в алерте")
    default void checkAlertLink() {
        alertLink.should().visible();
    }
}
