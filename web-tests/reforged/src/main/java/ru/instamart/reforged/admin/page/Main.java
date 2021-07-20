package ru.instamart.reforged.admin.page;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.checkpoint.MainCheck;
import ru.instamart.reforged.admin.element.MainElement;

public final class Main extends MainCheck implements AdminPage {

    public Main() {
        super(new MainElement());
    }

    @Step("Нажать на меню профиля")
    public void clickToProfileMenu() {
        element.user().click();
    }

    @Step("Нажать на кнопку выхода")
    public void clickToLogout() {
        element.logout().click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
