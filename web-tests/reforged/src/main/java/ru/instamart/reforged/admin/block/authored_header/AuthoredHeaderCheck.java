package ru.instamart.reforged.admin.block.authored_header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthoredHeaderCheck extends Check, AuthoredHeaderElement {

    @Step("Проверяем, что отображается adminNavigationTitle")
    default void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(adminNavigationTitle).isDisplayed();
    }

    @Step("Проверяем, что отображается adminName")
    default void checkAdminName() {
        waitAction().shouldBeVisible(adminName).isDisplayed();
    }

    @Step("Проверяем, что отображается adminAvatar")
    default void checkAdminAvatar() {
        waitAction().shouldBeVisible(adminAvatar).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutDropdown")
    default void checkLogoutDropdown() {
        waitAction().shouldBeVisible(logoutDropdown).isDisplayed();
    }
}
