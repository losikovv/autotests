package ru.instamart.reforged.admin;

import ru.instamart.reforged.admin.main.MainPage;
import ru.instamart.reforged.admin.shipment.ShipmentsPage;
import ru.instamart.reforged.admin.login.LoginPage;
import ru.instamart.reforged.admin.pages.new_page.NewPage;
import ru.instamart.reforged.admin.pages.Pages;
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

    public static ShipmentsPage shipments() {
        return (ShipmentsPage) getPage(ShipmentsPage.class);
    }

    public static MainPage main() {
        return (MainPage) getPage(MainPage.class);
    }

    private AdminRout() {}
}
