package ru.instamart.reforged.admin.block.authored_header;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthoredHeaderCheck extends Check, AuthoredHeaderElement {

    @Step("Проверяем, что отображается adminNavigationTitle")
    default void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(adminNavigationTitle);
    }

    @Step("Пользователь авторизовался")
    default void checkAdminAuth() {
        waitAction().shouldBeVisible(adminName);
    }

    @Step("Пользователь {0} авторизовался")
    default void checkUserName(final String userName) {
        Assert.assertEquals(adminName.getText(), userName, "Имя пользователя: " + adminName.getText() + " не соответствует ожидаемому: " + userName);
    }

    @Step("Проверяем, что отображается adminAvatar")
    default void checkAdminAvatar() {
        waitAction().shouldBeVisible(adminAvatar);
    }

    @Step("Проверяем, что отображается logoutDropdown")
    default void checkLogoutDropdown() {
        waitAction().shouldBeVisible(logoutDropdown);
    }

    @Step("Пользователь не авторизовался")
    default void checkIsNotAuth() {
        Kraken.waitAction().shouldNotBeVisible(adminName);
    }
}
