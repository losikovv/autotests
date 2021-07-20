package ru.instamart.reforged.admin.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;

public interface UsersCheck extends Check, UsersElement {

    @Step("Проверяем что найденный email найденного пользователя:{0} соответствует ожидаемому={1}")
    default void checkFoundUserEmail(final String actualEmail, final String expectedEmail) {
        assertEquals(actualEmail, expectedEmail, "Найден неверный пользователь");
    }
}
