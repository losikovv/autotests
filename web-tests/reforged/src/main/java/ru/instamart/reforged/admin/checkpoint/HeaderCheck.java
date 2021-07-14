package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.admin.element.HeaderElement;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

@RequiredArgsConstructor
public class HeaderCheck implements Check {
    protected final HeaderElement element;

    @Step("Проверяем, что отображается adminNavigationTitle")
    public void checkAdminNavigationTitle() {
        waitAction().shouldBeVisible(element.adminNavigationTitle()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminName")
    public void checkAdminName() {
        waitAction().shouldBeVisible(element.adminName()).isDisplayed();
    }

    @Step("Проверяем, что отображается adminAvatar")
    public void checkAdminAvatar() {
        waitAction().shouldBeVisible(element.adminAvatar()).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutDropdown")
    public void checkLogoutDropdown() {
        waitAction().shouldBeVisible(element.logoutDropdown()).isDisplayed();
    }

    @Step("Проверяем, что отображается logoutButton")
    public void checkLogoutButton() {
        waitAction().shouldBeVisible(element.logoutButton()).isDisplayed();
    }
}
