package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.block.AuthoredHeader;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AuthoredHeaderCheck implements AuthoredHeader extends Check {
    protected AuthoredHeader headerElement;


    @Step("Проверяем, что отображается adminNavigationTitle")
    default void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(adminNavigationTitle()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminName")
    default void checkAdminName() {
        waitAction().shouldBeVisible(headerElement.adminName()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminAvatar")
    default void checkAdminAvatar() {
        waitAction().shouldBeVisible(headerElement.adminAvatar()).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutDropdown")
    default void checkLogoutDropdown() {
        waitAction().shouldBeVisible(headerElement.logoutDropdown()).isDisplayed();
    }

}
