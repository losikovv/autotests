package ru.instamart.reforged.admin.page.main;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.side_menu.SideMenu;

public final class MainPage implements AdminPage, MainCheck {


    public SideMenu interactSideMenu() {
        return sideMenu;
    }

    public AuthoredHeader interactAuthoredHeader() {
        return authoredHeader;
    }

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
