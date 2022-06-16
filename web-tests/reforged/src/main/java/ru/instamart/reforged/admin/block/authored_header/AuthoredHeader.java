package ru.instamart.reforged.admin.block.authored_header;

import io.qameta.allure.Step;

public final class AuthoredHeader implements AuthoredHeaderCheck {

    @Step("Нажать на меню профиля")
    public void clickToProfileMenu() {
        adminName.hoverAndClick();
    }

    @Step("Нажать на выход")
    public void clickToLogout() {
        logoutButton.hoverAndClick();
    }
}
