package ru.instamart.reforged.admin.page.usersEdit;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.*;

public interface UsersEditEditCheck extends Check, UsersEditElement {

    @Step("Проверяем что email пользователя:{0} соответствует ожидаемому={1}")
    default void checkEditUserEmail(final String actualEmail, final String expectedEmail) {
        assertEquals(actualEmail, expectedEmail, "Неверный email");
    }

    @Step("Проверяем что чекбокс В2В выбран")
    default void checkB2BIsSelected() {
        assertTrue(b2bUser.checkboxState(), "чекбокс не выбран");
    }

    @Step("Проверяем что чекбокс B2B не выбран")
    default void checkB2BIsNotSelected() {
        assertFalse(b2bUser.checkboxState(), "чекбокс выбран");
    }
}
