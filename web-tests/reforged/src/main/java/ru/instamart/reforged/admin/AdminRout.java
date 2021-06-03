package ru.instamart.reforged.admin;

import ru.instamart.reforged.admin.page.Login;
import ru.instamart.reforged.admin.page.Main;
import ru.instamart.reforged.core.page.Router;

public final class AdminRout extends Router {

    public static Login login() {
        return (Login) getPage(Login.class);
    }

    public static Main main() {
        return (Main) getPage(Main.class);
    }

    private AdminRout() {}
}
