package ru.instamart.reforged.admin;

import ru.instamart.reforged.admin.page.main.MainPage;
import ru.instamart.reforged.admin.page.shipment.ShipmentsPage;
import ru.instamart.reforged.admin.page.login.LoginPage;
import ru.instamart.reforged.admin.page.pages.new_page.NewPage;
import ru.instamart.reforged.admin.page.pages.Pages;
import ru.instamart.reforged.admin.page.sections.*;
import ru.instamart.reforged.core.page.Router;

public final class AdminRout extends Router {

    public static LoginPage login() {
        return (LoginPage) getPage(LoginPage.class);
    }

    public static Pages pages() {
        return (Pages) getPage(Pages.class);
    }

    public static NewPage newPages() {
        return (NewPage) getPage(NewPage.class);
    }

    public static Imports imports() {
        return (Imports) getPage(Imports.class);
    }

    public static Marketing marketing() {
        return (Marketing) getPage(Marketing.class);
    }

    public static Products products() {
        return (Products) getPage(Products.class);
    }

    public static Retailers retailers() {
        return (Retailers) getPage(Retailers.class);
    }

    public static Settings settings() {
        return (Settings) getPage(Settings.class);
    }

    public static Staff staff() {
        return (Staff) getPage(Staff.class);
    }

    public static Users users() {
        return (Users) getPage(Users.class);
    }

    public static ShipmentsPage shipments() {
        return (ShipmentsPage) getPage(ShipmentsPage.class);
    }

    public static MainPage main() {
        return (MainPage) getPage(MainPage.class);
    }

    public static Oktell oktell() {
        return (Oktell) getPage(Oktell.class);
    }

    private AdminRout() {}
}
