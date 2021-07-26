package ru.instamart.reforged.admin.page.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.*;

public interface UsersCheck extends Check, UsersElement {

    @Step("Проверяем что email найденного пользователя:{0} соответствует ожидаемому={1}")
    default void checkFoundUserEmail(final String actualEmail, final String expectedEmail) {
        assertEquals(actualEmail, expectedEmail, "Найден неверный пользователь");
    }
}
