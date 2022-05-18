package ru.instamart.reforged.next.drawer.cookie;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CookieDrawerCheck extends Check, CookieDrawerElement {

    @Step("Проверка что отображается алерт с cookie")
    default void checkAlertVisible() {
        waitAction().shouldBeVisible(alert);
    }

    @Step("Проверка что не отображается алерт с cookie")
    default void checkAlertNotVisible() {
        waitAction().shouldNotBeVisible(alert);
    }

    @Step("Проверка отображения кнопки в алерте")
    default void checkAlertButton() {
        waitAction().shouldBeVisible(alertButton);
    }

    @Step("Проверка отображения ссылки в алерте")
    default void checkAlertLink() {
        waitAction().shouldBeVisible(alertLink);
    }
}
