package ru.instamart.reforged.stf.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface UserProfileCheck extends UserProfileElement, Check {

    @Step("Проверить что ФИО {0} соответствует ожидаемому значению {1}")
    default void checkFullName(final String expected, final String actual) {
        assertEquals(actual, expected, "ФИО не соответствует");
    }

    @Step("Проверить что Email {0} соответствует ожидаемому значению {1}")
    default void checkEmail(final String expected, final String actual) {
        assertEquals(actual, expected, "Email не соответствует");
    }

    @Step("Проверить что Телефон {0} соответствует ожидаемому значению {1}")
    default void checkPhone(final String expected, final String actual) {
        assertEquals(actual, expected, "Телефон не соответствует");
    }

    @Step("Алерт 'Данные успешно сохранены' отобразился")
    default void checkSaveAlert() {
        waitAction().shouldBeVisible(alert);
    }

    @Step("Алерт 'Данные успешно сохранены' скрылись")
    default void checkSaveAlertHide() {
        waitAction().shouldNotBeVisible(alert);
    }
}
