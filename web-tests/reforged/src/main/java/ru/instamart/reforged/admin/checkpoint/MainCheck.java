package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.admin.block.AuthoredHeader;
import ru.instamart.reforged.admin.element.MainElement;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

@RequiredArgsConstructor
public class MainCheck implements Check {

    protected final MainElement element;
    protected final AuthoredHeader headerElement;

    @Step("Проверяем, что отображается adminNavigationTitle")
    public void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(headerElement.adminNavigationTitle()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminName")
    public void checkAdminName() {
        waitAction().shouldBeVisible(headerElement.adminName()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminAvatar")
    public void checkAdminAvatar() {
        waitAction().shouldBeVisible(headerElement.adminAvatar()).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutDropdown")
    public void checkLogoutDropdown() {
        waitAction().shouldBeVisible(headerElement.logoutDropdown()).isDisplayed();
    }

    @Step("Пользователь авторизовался")
    public void checkAuth() {
        waitAction().shouldBeVisible(element.user()).isDisplayed();
    }
}
