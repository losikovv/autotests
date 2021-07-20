package ru.instamart.reforged.admin.main;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public final class MainPage implements AdminPage, MainCheck {

    @Step("Нажать на меню профиля")
    public void clickToProfileMenu() {
        user.click();
    }

    @Step("Нажать на кнопку выхода")
    public void clickToLogout() {
        logout.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
