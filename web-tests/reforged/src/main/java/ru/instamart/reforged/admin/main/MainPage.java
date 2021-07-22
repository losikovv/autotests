package ru.instamart.reforged.admin.main;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

import static ru.instamart.reforged.admin.AdminRout.login;

public final class MainPage implements AdminPage, MainCheck {

    @Step("Нажать на меню профиля")
    public void clickToProfileMenu() {
        user.click();
    }

    @Step("Нажать на кнопку выхода")
    public void clickToLogout() {
        logout.click();
    }

    @Step("Выйти из профиля")
    public void doLogout() {
        clickToProfileMenu();
        clickToLogout();
        login().checkTitle();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
