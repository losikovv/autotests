package ru.instamart.reforged.admin.page.main;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.side_menu.SideMenu;

import static ru.instamart.reforged.admin.AdminRout.login;

public final class MainPage implements AdminPage, MainCheck {

    public SideMenu interactSideMenu() {
        return sideMenu;
    }

    public AuthoredHeader interactAuthoredHeader() {
        return authoredHeader;
    }

    @Step("Выйти из профиля")
    public void doLogout() {
        authoredHeader.clickToProfileMenu();
        authoredHeader.clickToLogout();
        login().checkTitle();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
