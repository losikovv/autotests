package ru.instamart.reforged.admin.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.admin.users.UsersElement.UsersEdit.successFlash;

public interface UsersCheck extends Check, UsersElement {

    @Step("Проверяем что найденный email найденного пользователя:{0} соответствует ожидаемому={1}")
    default void checkFoundUserEmail(final String actualEmail, final String expectedEmail) {
        assertEquals(actualEmail, expectedEmail, "Найден неверный пользователь");
    }

    @Step("Проверяем что появилась нотификация об успешном сохранении")
    default void checkSuccessFlash() {
        //нужны ли проверки наличия нотификации
        Kraken.waitAction().shouldBeVisible(successFlash);
    }

    @Step("Проверяем что email пользователя:{0} соответствует ожидаемому={1}")
    default void checkEditUserEmail(final String actualEmail, final String expectedEmail) {
        //Kraken.waitAction().shouldBeVisible(successFlash);
        assertEquals(actualEmail, expectedEmail, "Неверный email");
    }
}
