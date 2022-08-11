package ru.instamart.reforged.admin.page.active_directory.login_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ActiveDirectoryLoginPageCheck extends Check, ActiveDirectoryLoginPageElement {

    @Step("Проверяем, что алерт неверного логина или пароля виден")
    default void checkWrongUserDataAlertVisible() {
        waitAction().shouldBeVisible(wrongLoginOrPassAlert);
    }

    @Step("Проверяем, что алерт неверного формата логина виден")
    default void checkWrongLoginFormatAlertVisible() {
        waitAction().shouldBeVisible(wrongLoginFormatAlert);
    }
}
