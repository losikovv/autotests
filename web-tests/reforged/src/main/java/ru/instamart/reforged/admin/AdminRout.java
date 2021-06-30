package ru.instamart.reforged.admin;

import ru.instamart.reforged.admin.page.Login;
import ru.instamart.reforged.admin.page.Main;
import ru.instamart.reforged.admin.page.Shipments;
import ru.instamart.reforged.admin.page.pages.New;
import ru.instamart.reforged.admin.page.pages.Pages;
import ru.instamart.reforged.core.page.Router;

public final class AdminRout extends Router {

    public static Login login() {
        return (Login) getPage(Login.class);
    }

    public static Pages pages() {
        return (Pages) getPage(Pages.class);
    }

    public static New newPages() {
        return (New) getPage(New.class);
    }

    public static Shipments shipments() {
        return (Shipments) getPage(Shipments.class);
    }

    public static Main main() {
        return (Main) getPage(Main.class);
    }

    private AdminRout() {}
}
